
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
import org.eclipse.jgit.storage.dht.spi.RefTransaction;

class DhtRefUpdate extends RefUpdate {
	private final DhtRefDatabase refdb;

	private final RepositoryKey repo;

	private final Database db;

	private RefKey refKey;

	private RefTransaction txn;

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
		Ref dst = getRef();
		if (deref)
			dst = dst.getLeaf();
		refKey = RefKey.create(repo
		try {
			txn = db.refs().newTransaction(refKey);

			RefData old = txn.getOldData();
			if (old == null || old == RefData.NONE) {
				setOldObjectId(null);

			} else {
				TinyProtobuf.Decoder d = old.decode();
				DECODE: for (;;) {
					switch (d.next()) {
					case 0:
						break DECODE;

					case RefData.TAG_SYMREF:
						setOldObjectId(null);
						break DECODE;

					case RefData.TAG_TARGET:
						setOldObjectId(RefData.IdWithChunk.decode(d.message()));
						break DECODE;

					default:
						d.skip();
						continue;
					}
				}
			}

			return true;
		} catch (TimeoutException e) {
			throw new DhtTimeoutException(e);
		}
	}

	@Override
	protected void unlock() {
		if (txn != null) {
			txn.abort();
			txn = null;
		}
	}

	@Override
	protected Result doUpdate(Result desiredResult) throws IOException {
		try {
			boolean r = txn.compareAndPut(newData());
			txn = null;
			postUpdate(r);
			return r ? desiredResult : Result.LOCK_FAILURE;
		} catch (TimeoutException e) {
			return Result.IO_FAILURE;
		}
	}

	private RefData newData() throws IOException {
		ObjectId nId = getNewObjectId();
		try {
			RevObject obj = rw.parseAny(nId);
			DhtReader ctx = (DhtReader) rw.getObjectReader();

			ChunkKey key = ctx.findChunk(nId);
			if (key != null)
				nId = new RefData.IdWithChunk(nId

			if (obj instanceof RevTag) {
				ObjectId pId = rw.peel(obj);
				key = ctx.findChunk(pId);
				pId = key != null ? new RefData.IdWithChunk(pId
				return RefData.peeled(nId
			} else if (obj != null)
				return RefData.peeled(nId
			else
				return RefData.id(nId);
		} catch (MissingObjectException e) {
			return RefData.id(nId);
		}
	}

	@Override
	protected Result doDelete(Result desiredResult) throws IOException {
		try {
			boolean r = txn.compareAndPut(RefData.NONE);
			txn = null;
			postUpdate(r);
			return r ? desiredResult : Result.LOCK_FAILURE;
		} catch (TimeoutException e) {
			return Result.IO_FAILURE;
		}
	}

	@Override
	protected Result doLink(String target) throws IOException {
		try {
			boolean r = txn.compareAndPut(RefData.symbolic(target));
			txn = null;
			postUpdate(r);
			if (r) {
				if (getRef().getStorage() == Ref.Storage.NEW)
					return Result.NEW;
				return Result.FORCED;
			}
			return Result.LOCK_FAILURE;
		} catch (TimeoutException e) {
			return Result.IO_FAILURE;
		}
	}

	private void postUpdate(boolean success) {
		if (success)
			getRefDatabase().clearCache();
	}
}
