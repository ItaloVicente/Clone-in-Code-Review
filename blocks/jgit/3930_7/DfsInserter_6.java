
package org.eclipse.jgit.storage.dfs;

import static org.eclipse.jgit.storage.dfs.DfsObjDatabase.PackSource.GC;
import static org.eclipse.jgit.storage.dfs.DfsObjDatabase.PackSource.UNREACHABLE_GARBAGE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.dfs.DfsObjDatabase.PackSource;
import org.eclipse.jgit.storage.file.PackIndex;
import org.eclipse.jgit.storage.pack.PackConfig;
import org.eclipse.jgit.storage.pack.PackWriter;
import org.eclipse.jgit.util.io.CountingOutputStream;

public class DfsGarbageCollector {
	private final DfsRepository repo;

	private final DfsRefDatabase refdb;

	private final DfsObjDatabase objdb;

	private final List<DfsPackDescription> newPackDesc;

	private final List<PackWriter.Statistics> newPackStats;

	private final List<DfsPackFile> newPackList;

	private DfsReader ctx;

	private PackConfig packConfig;

	private Map<String

	private List<DfsPackFile> packsBefore;

	private Set<ObjectId> allHeads;

	private Set<ObjectId> nonHeads;

	private long objectsBefore;

	private long objectsPacked;

	private Set<ObjectId> tagTargets;

	public DfsGarbageCollector(DfsRepository repository) {
		repo = repository;
		refdb = repo.getRefDatabase();
		objdb = repo.getObjectDatabase();
		newPackDesc = new ArrayList<DfsPackDescription>(4);
		newPackStats = new ArrayList<PackWriter.Statistics>(4);
		newPackList = new ArrayList<DfsPackFile>(4);

		packConfig = new PackConfig(repo);
		packConfig.setIndexVersion(2);
	}

	public PackConfig getPackConfig() {
		return packConfig;
	}

	public DfsGarbageCollector setPackConfig(PackConfig newConfig) {
		packConfig = newConfig;
		return this;
	}

	public boolean pack(ProgressMonitor pm) throws IOException {
		if (pm == null)
			pm = NullProgressMonitor.INSTANCE;
		if (packConfig.getIndexVersion() != 2)
			throw new IllegalStateException("Only index version 2");

		ctx = (DfsReader) objdb.newReader();
		try {
			refdb.clearCache();
			objdb.clearCache();

			refsBefore = repo.getAllRefs();
			packsBefore = Arrays.asList(objdb.getPacks());
			if (packsBefore.isEmpty())
				return true;

			allHeads = new HashSet<ObjectId>();
			nonHeads = new HashSet<ObjectId>();
			tagTargets = new HashSet<ObjectId>();
			for (Ref ref : refsBefore.values()) {
				if (ref.isSymbolic() || ref.getObjectId() == null)
					continue;
				if (isHead(ref))
					allHeads.add(ref.getObjectId());
				else
					nonHeads.add(ref.getObjectId());
				if (ref.getPeeledObjectId() != null)
					tagTargets.add(ref.getPeeledObjectId());
			}
			tagTargets.addAll(allHeads);

			boolean rollback = true;
			try {
				packHeads(pm);
				packRest(pm);
				packGarbage(pm);
				objdb.commitPack(newPackDesc
				rollback = false;
				return true;
			} finally {
				if (rollback)
					objdb.rollbackPack(newPackDesc);
			}
		} finally {
			ctx.release();
		}
	}

	public List<DfsPackDescription> getSourcePacks() {
		return toPrune();
	}

	public List<DfsPackDescription> getNewPacks() {
		return newPackDesc;
	}

	public List<PackWriter.Statistics> getNewPackStatistics() {
		return newPackStats;
	}

	private List<DfsPackDescription> toPrune() {
		int cnt = packsBefore.size();
		List<DfsPackDescription> all = new ArrayList<DfsPackDescription>(cnt);
		for (DfsPackFile pack : packsBefore)
			all.add(pack.getPackDescription());
		return all;
	}

	private void packHeads(ProgressMonitor pm) throws IOException {
		if (allHeads.isEmpty())
			return;

		PackWriter pw = newPackWriter();
		try {
			pw.preparePack(pm
			if (0 < pw.getObjectCount())
				writePack(GC
		} finally {
			pw.release();
		}
	}

	private void packRest(ProgressMonitor pm) throws IOException {
		if (nonHeads.isEmpty() || objectsPacked == getObjectsBefore())
			return;

		PackWriter pw = newPackWriter();
		try {
			for (DfsPackFile pack : newPackList)
				pw.excludeObjects(pack.getPackIndex(ctx));
			pw.preparePack(pm
			if (0 < pw.getObjectCount())
				writePack(GC
		} finally {
			pw.release();
		}
	}

	private void packGarbage(ProgressMonitor pm) throws IOException {
		if (objectsPacked == getObjectsBefore())
			return;

		List<PackIndex> newIdx = new ArrayList<PackIndex>(newPackList.size());
		for (DfsPackFile pack : newPackList)
			newIdx.add(pack.getPackIndex(ctx));

		PackWriter pw = newPackWriter();
		try {
			RevWalk pool = new RevWalk(ctx);
			for (DfsPackFile oldPack : packsBefore) {
				PackIndex oldIdx = oldPack.getPackIndex(ctx);
				pm.beginTask("Finding garbage"
				for (PackIndex.MutableEntry ent : oldIdx) {
					pm.update(1);
					ObjectId id = ent.toObjectId();
					if (pool.lookupOrNull(id) != null || anyIndexHas(newIdx
						continue;

					int type = oldPack.getObjectType(ctx
					pw.addObject(pool.lookupAny(id
				}
				pm.endTask();
			}
			if (0 < pw.getObjectCount())
				writePack(UNREACHABLE_GARBAGE
		} finally {
			pw.release();
		}
	}

	private static boolean anyIndexHas(List<PackIndex> list
		for (PackIndex idx : list)
			if (idx.hasObject(id))
				return true;
		return false;
	}

	private static boolean isHead(Ref ref) {
		return ref.getName().startsWith(Constants.R_HEADS);
	}

	private long getObjectsBefore() {
		if (objectsBefore == 0) {
			for (DfsPackFile p : packsBefore)
				objectsBefore += p.getPackDescription().getObjectCount();
		}
		return objectsBefore;
	}

	private PackWriter newPackWriter() {
		PackWriter pw = new PackWriter(packConfig
		pw.setDeltaBaseAsOffset(true);
		pw.setReuseDeltaCommits(false);
		pw.setTagTargets(tagTargets);
		return pw;
	}

	private DfsPackDescription writePack(PackSource source
			ProgressMonitor pm) throws IOException {
		DfsOutputStream out;
		DfsPackDescription pack = repo.getObjectDatabase().newPack(source);
		newPackDesc.add(pack);

		out = objdb.writePackFile(pack);
		try {
			pw.writePack(pm
		} finally {
			out.close();
		}

		out = objdb.writePackIndex(pack);
		try {
			CountingOutputStream cnt = new CountingOutputStream(out);
			pw.writeIndex(cnt);
			pack.setIndexSize(cnt.getCount());
		} finally {
			out.close();
		}

		PackWriter.Statistics stats = pw.getStatistics();
		pack.setPackStats(stats);
		pack.setPackSize(stats.getTotalBytes());
		pack.setObjectCount(stats.getTotalObjects());
		pack.setDeltaCount(stats.getTotalDeltas());
		objectsPacked += stats.getTotalObjects();
		newPackStats.add(stats);
		newPackList.add(DfsBlockCache.getInstance().getOrCreate(pack
		return pack;
	}
}
