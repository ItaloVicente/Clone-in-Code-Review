
package org.eclipse.jgit.storage.dfs;

import static org.eclipse.jgit.storage.dfs.DfsObjDatabase.PackSource.COMPACT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.revwalk.RevFlag;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.PackIndex;
import org.eclipse.jgit.storage.pack.PackConfig;
import org.eclipse.jgit.storage.pack.PackWriter;
import org.eclipse.jgit.util.BlockList;
import org.eclipse.jgit.util.io.CountingOutputStream;

public class DfsPackCompactor {
	private final DfsRepository repo;

	private final List<DfsPackFile> packs;

	private int autoAddSize;

	public DfsPackCompactor(DfsRepository repository) {
		repo = repository;
		packs = new ArrayList<DfsPackFile>();
	}

	public DfsPackCompactor add(DfsPackFile pack) {
		packs.add(pack);
		return this;
	}

	public DfsPackCompactor autoAdd() throws IOException {
		DfsObjDatabase objdb = repo.getObjectDatabase();
		for (DfsPackFile pack : objdb.getPacks()) {
			DfsPackDescription d = pack.getPackDescription();
			if (d.getPackSize() < autoAddSize)
				add(pack);
		}
		return this;
	}

	public void compact(ProgressMonitor pm) throws IOException {
		if (pm == null)
			pm = NullProgressMonitor.INSTANCE;

		DfsObjDatabase objdb = repo.getObjectDatabase();
		DfsReader ctx = (DfsReader) objdb.newReader();
		try {
			PackConfig pc = new PackConfig(repo);
			pc.setIndexVersion(2);
			pc.setDeltaCompress(false);
			pc.setReuseDeltas(true);
			pc.setReuseObjects(true);

			PackWriter pw = new PackWriter(pc
			try {
				pw.setDeltaBaseAsOffset(true);
				pw.setReuseDeltaCommits(false);

				addObjectsToPack(pw
				if (pw.getObjectCount() == 0)
					return;

				boolean rollback = true;
				DfsPackDescription pack = objdb.newPack(COMPACT);
				try {
					writePack(objdb
					writeIndex(objdb
					objdb.commitPack(Collections.singletonList(pack)
					rollback = false;
				} finally {
					if (rollback)
						objdb.rollbackPack(Collections.singletonList(pack));
				}
			} finally {
				pw.release();
			}
		} finally {
			ctx.release();
		}
	}

	private List<DfsPackDescription> toPrune() {
		int cnt = packs.size();
		List<DfsPackDescription> all = new ArrayList<DfsPackDescription>(cnt);
		for (DfsPackFile pack : packs)
			all.add(pack.getPackDescription());
		return all;
	}

	private void addObjectsToPack(PackWriter pw
			ProgressMonitor pm) throws IOException
			IncorrectObjectTypeException {
		Collections.sort(packs
			public int compare(DfsPackFile a
				return a.getPackDescription().compareTo(b.getPackDescription());
			}
		});

		RevWalk rw = new RevWalk(ctx);
		RevFlag added = rw.newFlag("ADDED");

		pm.beginTask(JGitText.get().countingObjects
		for (DfsPackFile src : packs) {
			List<ObjectIdWithOffset> want = new BlockList<ObjectIdWithOffset>();
			for (PackIndex.MutableEntry ent : src.getPackIndex(ctx)) {
				ObjectId id = ent.toObjectId();
				RevObject obj = rw.lookupOrNull(id);
				if (obj == null || !obj.has(added))
					want.add(new ObjectIdWithOffset(id
			}

			Collections.sort(want
				public int compare(ObjectIdWithOffset a
					return Long.signum(a.offset - b.offset);
				}
			});

			for (ObjectIdWithOffset id : want) {
				int type = src.getObjectType(ctx
				RevObject obj = rw.lookupAny(id
				if (!obj.has(added)) {
					pm.update(1);
					pw.addObject(obj);
					obj.add(added);
				}
			}
		}
		pm.endTask();
	}

	private void writePack(DfsObjDatabase objdb
			PackWriter pw
		DfsOutputStream out = objdb.writePackFile(pack);
		try {
			CountingOutputStream cnt = new CountingOutputStream(out);
			pw.writePack(pm
			pack.setObjectCount(pw.getObjectCount());
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

	private static class ObjectIdWithOffset extends ObjectId {
		final long offset;

		ObjectIdWithOffset(AnyObjectId id
			super(id);
			offset = ofs;
		}
	}
}
