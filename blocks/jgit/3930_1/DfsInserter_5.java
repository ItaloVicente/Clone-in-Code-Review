
package org.eclipse.jgit.storage.dfs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.PackIndex;
import org.eclipse.jgit.storage.pack.PackConfig;
import org.eclipse.jgit.storage.pack.PackWriter;
import org.eclipse.jgit.util.io.CountingOutputStream;

public class DfsGarbageCollector {
	private final DfsRepository repo;

	private PackConfig packConfig;

	private long pruneExpire;

	private Map<String

	private List<DfsPackDescription> prunePacks;

	private DfsPackFile newPack;

	private PackIndex newIndex;

	private DfsReader ctx;

	private RevWalk rw;

	public DfsGarbageCollector(DfsRepository repository) {
		repo = repository;

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

	public long getPruneExpire() {
		return pruneExpire;
	}

	public DfsGarbageCollector setPruneExpire(long expires) {
		pruneExpire = Math.max(0
		return this;
	}

	public DfsPackDescription pack(ProgressMonitor pm) throws IOException {
		if (packConfig.getIndexVersion() != 2)
			throw new IllegalStateException("Only index version 2");
		if (pm == null)
			pm = NullProgressMonitor.INSTANCE;

		DfsObjDatabase objdb = repo.getObjectDatabase();
		try {
			repo.getRefDatabase().clearCache();
			refsBefore = repo.getAllRefs();
			Set<ObjectId> tipsPacked = new HashSet<ObjectId>();
			for (Ref ref : refsBefore.values()) {
				if (ref.getObjectId() != null)
					tipsPacked.add(ref.getObjectId());
			}

			objdb.clearCache();
			DfsPackFile[] packsBefore = objdb.getPacks();
			ctx = (DfsReader) objdb.newReader();

			PackWriter pw = new PackWriter(packConfig
			pw.setDeltaBaseAsOffset(true);
			pw.setReuseDeltaCommits(false);
			pw.preparePack(pm

			boolean rollback = true;
			DfsPackDescription pack = objdb.newPack(pw.getObjectCount());
			try {
				writePack(objdb
				writeIndex(objdb

				newPack = DfsBlockCache.getInstance().getOrCreate(pack

				if (isSafeToPruneOldPacks(tipsPacked))
					prunePacks = filterPacks(packsBefore);
				else
					prunePacks = Collections.emptyList();

				rollback = false;
				return pack;
			} finally {
				if (rollback)
					objdb.rollbackPack(pack);
			}
		} finally {
			ctx.release();
			ctx = null;
			rw = null;
			newIndex = null;
		}
	}

	public List<DfsPackDescription> getPacksToPrune() {
		return prunePacks;
	}

	private List<DfsPackDescription> filterPacks(DfsPackFile[] packList)
			throws IOException {
		long expireTime = newPack.getPackDescription().getLastModified()
				- pruneExpire;
		List<DfsPackDescription> safe;

		safe = new ArrayList<DfsPackDescription>(packList.length);
		PACK_LIST: for (DfsPackFile oldPack : packList) {
			if (oldPack.getPackDescription().getLastModified() <= expireTime) {
				safe.add(oldPack.getPackDescription());
				continue;
			}

			if (newIndex == null)
				newIndex = newPack.getPackIndex(ctx);
			for (PackIndex.MutableEntry ent : oldPack.getPackIndex(ctx)) {
				if (!newIndex.hasObject(ent.toObjectId()))
					continue PACK_LIST;
			}
			safe.add(oldPack.getPackDescription());
		}
		return safe;
	}

	private boolean isSafeToPruneOldPacks(Set<ObjectId> tipsPacked)
			throws IOException {
		repo.getRefDatabase().clearCache();

		for (Ref ref : repo.getAllRefs().values()) {
			if (ref.isSymbolic() || ref.getObjectId() == null)
				continue;

			if (tipsPacked.contains(ref.getObjectId()))
				continue;

			if (isFastForward(ref))
				continue;

			if (newIndex == null)
				newIndex = newPack.getPackIndex(ctx);
			if (newIndex.hasObject(ref.getObjectId()))
				continue;

			return false;
		}

		return true;
	}

	private boolean isFastForward(Ref newRef) throws IOException {
		Ref oldRef = refsBefore.get(newRef.getName());
		if (oldRef == null || oldRef.getObjectId() == null)
			return false;

		if (ctx == null)
			ctx = (DfsReader) repo.newObjectReader();
		if (rw == null)
			rw = new RevWalk(ctx);

		try {
			RevObject oldObj = rw.parseAny(oldRef.getObjectId());
			RevObject newObj = rw.parseAny(newRef.getObjectId());

			if (oldObj instanceof RevCommit && newObj instanceof RevCommit)
				return rw.isMergedInto((RevCommit) oldObj
			else
				return false;
		} catch (IncorrectObjectTypeException notCommit) {
			return false;
		}
	}

	private void writePack(DfsObjDatabase objdb
			PackWriter pw
		DfsOutputStream out = objdb.writePackFile(pack);
		try {
			CountingOutputStream cnt = new CountingOutputStream(out);
			pw.writePack(pm
			pack.setPackSize(cnt.getCount());
		} finally {
			out.close();
		}
	}

	private void writeIndex(DfsObjDatabase objdb
			PackWriter pw) throws IOException {
		DfsOutputStream out = objdb.writePackIndex(pack);
		try {
			CountingOutputStream cnt = new CountingOutputStream(out);
			pw.writeIndex(cnt);
			pack.setIndexSize(cnt.getCount());
		} finally {
			out.close();
		}
	}
}
