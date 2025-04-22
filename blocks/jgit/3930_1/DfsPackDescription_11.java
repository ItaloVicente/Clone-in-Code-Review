
package org.eclipse.jgit.storage.dfs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
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

	public DfsPackDescription compact() throws IOException {
		DfsObjDatabase objdb = repo.getObjectDatabase();
		DfsReader ctx = (DfsReader) objdb.newReader();
		try {
			PackConfig pc = new PackConfig(repo);
			pc.setIndexVersion(2);
			pc.setDeltaCompress(false);
			pc.setReuseDeltas(true);
			pc.setReuseObjects(true);

			PackWriter pw = new PackWriter(pc
			pw.setDeltaBaseAsOffset(true);
			pw.setReuseDeltaCommits(false);

			addObjectsToPack(pw

			boolean rollback = true;
			DfsPackDescription pack = objdb.newPack(pw.getObjectCount());
			try {
				writePack(objdb
				writeIndex(objdb
				rollback = false;
				return pack;
			} finally {
				if (rollback)
					objdb.rollbackPack(pack);
			}
		} finally {
			ctx.release();
		}
	}

	public List<DfsPackDescription> getPacksToPrune() {
		int cnt = packs.size();
		List<DfsPackDescription> all = new ArrayList<DfsPackDescription>(cnt);
		for (DfsPackFile pack : packs)
			all.add(pack.getPackDescription());
		return all;
	}

	private void addObjectsToPack(PackWriter pw
			throws IOException
		Collections.sort(packs
			public int compare(DfsPackFile a
				return a.getPackDescription().compareTo(b.getPackDescription());
			}
		});

		RevWalk rw = new RevWalk(ctx);
		RevFlag added = rw.newFlag("ADDED");

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
					pw.addObject(obj);
					obj.add(added);
				}
			}
		}
	}

	private void writePack(DfsObjDatabase objdb
			PackWriter pw) throws IOException {
		DfsOutputStream out = objdb.writePackFile(pack);
		try {
			CountingOutputStream cnt = new CountingOutputStream(out);
			pw.writePack(NullProgressMonitor.INSTANCE
					NullProgressMonitor.INSTANCE
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
