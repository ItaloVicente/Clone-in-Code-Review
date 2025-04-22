
package org.eclipse.jgit.internal.storage.dfs;

import static org.eclipse.jgit.internal.storage.dfs.DfsObjDatabase.PackSource.COMPACT;
import static org.eclipse.jgit.internal.storage.dfs.DfsObjDatabase.PackSource.GC;
import static org.eclipse.jgit.internal.storage.pack.PackExt.INDEX;
import static org.eclipse.jgit.internal.storage.pack.PackExt.PACK;
import static org.eclipse.jgit.internal.storage.pack.PackExt.REFTABLE;
import static org.eclipse.jgit.internal.storage.pack.StoredObjectRepresentation.PACK_DELTA;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.file.PackIndex;
import org.eclipse.jgit.internal.storage.file.PackReverseIndex;
import org.eclipse.jgit.internal.storage.pack.PackWriter;
import org.eclipse.jgit.internal.storage.reftable.ReftableCompactor;
import org.eclipse.jgit.internal.storage.reftable.ReftableConfig;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdSet;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.revwalk.RevFlag;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.pack.PackConfig;
import org.eclipse.jgit.storage.pack.PackStatistics;
import org.eclipse.jgit.util.BlockList;
import org.eclipse.jgit.util.io.CountingOutputStream;

public class DfsPackCompactor {
	private final DfsRepository repo;
	private final List<DfsPackFile> srcPacks;
	private final List<DfsReftable> srcReftables;
	private final List<ObjectIdSet> exclude;

	private PackStatistics newStats;
	private DfsPackDescription outDesc;

	private int autoAddSize;
	private ReftableConfig reftableConfig;

	private RevWalk rw;
	private RevFlag added;
	private RevFlag isBase;

	public DfsPackCompactor(DfsRepository repository) {
		repo = repository;
		srcPacks = new ArrayList<>();
		srcReftables = new ArrayList<>();
		exclude = new ArrayList<>(4);
	}

	public DfsPackCompactor setReftableConfig(ReftableConfig cfg) {
		reftableConfig = cfg;
		return this;
	}

	public DfsPackCompactor add(DfsPackFile pack) {
		srcPacks.add(pack);
		return this;
	}

	public DfsPackCompactor add(DfsReftable table) {
		srcReftables.add(table);
		return this;
	}

	public DfsPackCompactor autoAdd() throws IOException {
		DfsObjDatabase objdb = repo.getObjectDatabase();
		for (DfsPackFile pack : objdb.getPacks()) {
			DfsPackDescription d = pack.getPackDescription();
			if (d.getFileSize(PACK) < autoAddSize)
				add(pack);
			else
				exclude(pack);
		}

		if (reftableConfig != null) {
			for (DfsReftable table : objdb.getReftables()) {
				DfsPackDescription d = table.getPackDescription();
				if (d.getPackSource() != GC
						&& d.getFileSize(REFTABLE) < autoAddSize) {
					add(table);
				}
			}
		}
		return this;
	}

	public DfsPackCompactor exclude(ObjectIdSet set) {
		exclude.add(set);
		return this;
	}

	public DfsPackCompactor exclude(DfsPackFile pack) throws IOException {
		final PackIndex idx;
		try (DfsReader ctx = (DfsReader) repo.newObjectReader()) {
			idx = pack.getPackIndex(ctx);
		}
		return exclude(idx);
	}

	public void compact(ProgressMonitor pm) throws IOException {
		if (pm == null) {
			pm = NullProgressMonitor.INSTANCE;
		}

		DfsObjDatabase objdb = repo.getObjectDatabase();
		try (DfsReader ctx = objdb.newReader()) {
			if (reftableConfig != null && !srcReftables.isEmpty()) {
				compactReftables(ctx);
			}
			compactPacks(ctx

			List<DfsPackDescription> commit = getNewPacks();
			Collection<DfsPackDescription> remove = toPrune();
			if (!commit.isEmpty() || !remove.isEmpty()) {
				objdb.commitPack(commit
			}
		} finally {
			rw = null;
		}
	}

	private void compactPacks(DfsReader ctx
			throws IOException
		DfsObjDatabase objdb = repo.getObjectDatabase();
		PackConfig pc = new PackConfig(repo);
		pc.setIndexVersion(2);
		pc.setDeltaCompress(false);
		pc.setReuseDeltas(true);
		pc.setReuseObjects(true);

		try (PackWriter pw = new PackWriter(pc
			pw.setDeltaBaseAsOffset(true);
			pw.setReuseDeltaCommits(false);

			addObjectsToPack(pw
			if (pw.getObjectCount() == 0) {
				return;
			}

			boolean rollback = true;
			initOutDesc(objdb);
			try {
				writePack(objdb
				writeIndex(objdb

				PackStatistics stats = pw.getStatistics();

				outDesc.setPackStats(stats);
				newStats = stats;
				rollback = false;
			} finally {
				if (rollback) {
					objdb.rollbackPack(Collections.singletonList(outDesc));
				}
			}
		}
	}

	private long estimatePackSize() {
		long size = 32;
		for (DfsPackFile pack : srcPacks) {
			size += pack.getPackDescription().getFileSize(PACK) - 32;
		}
		return size;
	}

	private void compactReftables(DfsReader ctx) throws IOException {
		DfsObjDatabase objdb = repo.getObjectDatabase();
		Collections.sort(srcReftables

		try (ReftableStack stack = ReftableStack.open(ctx
			initOutDesc(objdb);
			ReftableCompactor compact = new ReftableCompactor();
			compact.addAll(stack.readers());
			compact.setIncludeDeletes(true);
			writeReftable(objdb
		}
	}

	private void initOutDesc(DfsObjDatabase objdb) throws IOException {
		if (outDesc == null) {
			outDesc = objdb.newPack(COMPACT
		}
	}

	public Collection<DfsPackDescription> getSourcePacks() {
		Set<DfsPackDescription> src = new HashSet<>();
		for (DfsPackFile pack : srcPacks) {
			src.add(pack.getPackDescription());
		}
		for (DfsReftable table : srcReftables) {
			src.add(table.getPackDescription());
		}
		return src;
	}

	public List<DfsPackDescription> getNewPacks() {
		return outDesc != null
				? Collections.singletonList(outDesc)
				: Collections.emptyList();
	}

	public List<PackStatistics> getNewPackStatistics() {
		return outDesc != null
				? Collections.singletonList(newStats)
				: Collections.emptyList();
	}

	private Collection<DfsPackDescription> toPrune() {
		Set<DfsPackDescription> packs = new HashSet<>();
		for (DfsPackFile pack : srcPacks) {
			packs.add(pack.getPackDescription());
		}

		Set<DfsPackDescription> reftables = new HashSet<>();
		for (DfsReftable table : srcReftables) {
			reftables.add(table.getPackDescription());
		}

		for (Iterator<DfsPackDescription> i = packs.iterator(); i.hasNext();) {
			DfsPackDescription d = i.next();
			if (d.hasFileExt(REFTABLE) && !reftables.contains(d)) {
				i.remove();
			}
		}

		for (Iterator<DfsPackDescription> i = reftables.iterator();
				i.hasNext();) {
			DfsPackDescription d = i.next();
			if (d.hasFileExt(PACK) && !packs.contains(d)) {
				i.remove();
			}
		}

		Set<DfsPackDescription> toPrune = new HashSet<>();
		toPrune.addAll(packs);
		toPrune.addAll(reftables);
		return toPrune;
	}

	private void addObjectsToPack(PackWriter pw
			ProgressMonitor pm) throws IOException
			IncorrectObjectTypeException {
		Collections.sort(
				srcPacks
				Comparator.comparing(
						DfsPackFile::getPackDescription
						DfsPackDescription.objectLookupComparator()));

		rw = new RevWalk(ctx);
		List<RevObject> baseObjects = new BlockList<>();

		pm.beginTask(JGitText.get().countingObjects
		for (DfsPackFile src : srcPacks) {
			List<ObjectIdWithOffset> want = toInclude(src
			if (want.isEmpty())
				continue;

			PackReverseIndex rev = src.getReverseIdx(ctx);
			DfsObjectRepresentation rep = new DfsObjectRepresentation(src);
			for (ObjectIdWithOffset id : want) {
				int type = src.getObjectType(ctx
				RevObject obj = rw.lookupAny(id
				if (obj.has(added))
					continue;

				pm.update(1);
				pw.addObject(obj);
				obj.add(added);

				src.representation(rep
				if (rep.getFormat() != PACK_DELTA)
					continue;

				RevObject base = rw.lookupAny(rep.getDeltaBase()
				if (!base.has(added) && !base.has(isBase)) {
					baseObjects.add(base);
					base.add(isBase);
				}
			}
		}
		for (RevObject obj : baseObjects) {
			if (!obj.has(added)) {
				pm.update(1);
				pw.addObject(obj);
				obj.add(added);
			}
		}
		pm.endTask();
	}

	private List<ObjectIdWithOffset> toInclude(DfsPackFile src
			throws IOException {
		PackIndex srcIdx = src.getPackIndex(ctx);
		List<ObjectIdWithOffset> want = new BlockList<>(
				(int) srcIdx.getObjectCount());
		SCAN: for (PackIndex.MutableEntry ent : srcIdx) {
			ObjectId id = ent.toObjectId();
			RevObject obj = rw.lookupOrNull(id);
			if (obj != null && (obj.has(added) || obj.has(isBase)))
				continue;
			for (ObjectIdSet e : exclude)
				if (e.contains(id))
					continue SCAN;
			want.add(new ObjectIdWithOffset(id
		}
		Collections.sort(want
			@Override
			public int compare(ObjectIdWithOffset a
				return Long.signum(a.offset - b.offset);
			}
		});
		return want;
	}

	private static void writePack(DfsObjDatabase objdb
			DfsPackDescription pack
			PackWriter pw
		try (DfsOutputStream out = objdb.writeFile(pack
			pw.writePack(pm
			pack.addFileExt(PACK);
			pack.setBlockSize(PACK
		}
	}

	private static void writeIndex(DfsObjDatabase objdb
			DfsPackDescription pack
			PackWriter pw) throws IOException {
		try (DfsOutputStream out = objdb.writeFile(pack
			CountingOutputStream cnt = new CountingOutputStream(out);
			pw.writeIndex(cnt);
			pack.addFileExt(INDEX);
			pack.setFileSize(INDEX
			pack.setBlockSize(INDEX
			pack.setIndexVersion(pw.getIndexVersion());
		}
	}

	private void writeReftable(DfsObjDatabase objdb
			ReftableCompactor compact) throws IOException {
		try (DfsOutputStream out = objdb.writeFile(pack
			compact.setConfig(configureReftable(reftableConfig
			compact.compact(out);
			pack.addFileExt(REFTABLE);
			pack.setReftableStats(compact.getStats());
		}
	}

	static ReftableConfig configureReftable(ReftableConfig cfg
			DfsOutputStream out) {
		int bs = out.blockSize();
		if (bs > 0) {
			cfg = new ReftableConfig(cfg);
			cfg.setRefBlockSize(bs);
			cfg.setAlignBlocks(true);
		}
		return cfg;
	}

	private static class ObjectIdWithOffset extends ObjectId {
		final long offset;

		ObjectIdWithOffset(AnyObjectId id
			super(id);
			offset = ofs;
		}
	}
}
