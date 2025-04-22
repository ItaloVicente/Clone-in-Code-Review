
package org.eclipse.jgit.storage.dht;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.dht.spi.Database;

class DhtRefUpdate extends RefUpdate {
	private final DhtRefDatabase refdb;

	private final RepositoryKey repo;

	private final Database db;

	private RefKey refKey;

	private RefData oldData;

	private RefData newData;

	private Ref dstRef;

	private RevWalk rw;

	DhtRefUpdate(DhtRefDatabase refdb
		super(ref);
		this.refdb = refdb;
		this.repo = repo;
		this.db = db;
	}

	@Override
	protected DhtRefDatabase getRefDatabase() {
		return refdb;
	}

	@Override
	protected DhtRepository getRepository() {
		return refdb.getRepository();
	}

	@Override
	public Result update(RevWalk walk) throws IOException {
		try {
			rw = walk;
			return super.update(walk);
		} finally {
			rw = null;
		}
	}

	@Override
	protected boolean tryLock(boolean deref) throws IOException {
		dstRef = getRef();
		if (deref)
			dstRef = dstRef.getLeaf();

		refKey = RefKey.create(repo
		oldData = RefData.fromRef(dstRef);

		if (dstRef.isSymbolic())
			setOldObjectId(null);
		else
			setOldObjectId(dstRef.getObjectId());

		return true;
	}

	@Override
	protected void unlock() {
	}

	@Override
	protected Result doUpdate(Result desiredResult) throws IOException {
		try {
			newData = newData();
			boolean r = db.ref().compareAndPut(refKey
			if (r) {
				getRefDatabase().stored(dstRef.getName()
				return desiredResult;
			} else {
				getRefDatabase().clearCache();
				return Result.LOCK_FAILURE;
			}
		} catch (TimeoutException e) {
			return Result.IO_FAILURE;
		}
	}

	@Override
	protected Result doDelete(Result desiredResult) throws IOException {
		try {
			boolean r = db.ref().compareAndRemove(refKey
			if (r) {
				getRefDatabase().removed(dstRef.getName());
				return desiredResult;
			} else {
				getRefDatabase().clearCache();
				return Result.LOCK_FAILURE;
			}
		} catch (TimeoutException e) {
			return Result.IO_FAILURE;
		}
	}

	@Override
	protected Result doLink(String target) throws IOException {
		try {
			newData = RefData.symbolic(target);
			boolean r = db.ref().compareAndPut(refKey
			if (r) {
				getRefDatabase().stored(dstRef.getName()
				if (getRef().getStorage() == Ref.Storage.NEW)
					return Result.NEW;
				return Result.FORCED;
			} else {
				getRefDatabase().clearCache();
				return Result.LOCK_FAILURE;
			}
		} catch (TimeoutException e) {
			return Result.IO_FAILURE;
		}
	}

	private RefData newData() throws IOException {
		ObjectId newId = getNewObjectId();
		try {
			RevObject obj = rw.parseAny(newId);
			DhtReader ctx = (DhtReader) rw.getObjectReader();

			ChunkKey key = ctx.findChunk(newId);
			if (key != null)
				newId = new RefData.IdWithChunk(newId

			if (obj instanceof RevTag) {
				ObjectId pId = rw.peel(obj);
				key = ctx.findChunk(pId);
				pId = key != null ? new RefData.IdWithChunk(pId
				return RefData.peeled(newId
			} else if (obj != null)
				return RefData.peeled(newId
			else
				return RefData.id(newId);
		} catch (MissingObjectException e) {
			return RefData.id(newId);
		}
	}
}
