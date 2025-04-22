
package org.eclipse.jgit.storage.dfs;

import java.io.IOException;

import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.SymbolicRef;
import org.eclipse.jgit.lib.Ref.Storage;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevWalk;

final class DfsRefUpdate extends RefUpdate {
	private final DfsRefDatabase refdb;

	private Ref dstRef;

	private RevWalk rw;

	DfsRefUpdate(DfsRefDatabase refdb
		super(ref);
		this.refdb = refdb;
	}

	@Override
	protected DfsRefDatabase getRefDatabase() {
		return refdb;
	}

	@Override
	protected DfsRepository getRepository() {
		return refdb.getRepository();
	}

	@Override
	protected boolean tryLock(boolean deref) throws IOException {
		dstRef = getRef();
		if (deref)
			dstRef = dstRef.getLeaf();

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
	public Result update(RevWalk walk) throws IOException {
		try {
			rw = walk;
			return super.update(walk);
		} finally {
			rw = null;
		}
	}

	@Override
	protected Result doUpdate(Result desiredResult) throws IOException {
		ObjectIdRef newRef;
		RevObject obj = rw.parseAny(getNewObjectId());
		if (obj instanceof RevTag) {
			newRef = new ObjectIdRef.PeeledTag(
					Storage.PACKED
					dstRef.getName()
					getNewObjectId()
					rw.peel(obj).copy());
		} else {
			newRef = new ObjectIdRef.PeeledNonTag(
					Storage.PACKED
					dstRef.getName()
					getNewObjectId());
		}

		if (getRefDatabase().compareAndPut(dstRef
			getRefDatabase().stored(newRef);
			return desiredResult;
		}
		return Result.LOCK_FAILURE;
	}

	@Override
	protected Result doDelete(Result desiredResult) throws IOException {
		if (getRefDatabase().compareAndRemove(dstRef)) {
			getRefDatabase().removed(dstRef.getName());
			return desiredResult;
		}
		return Result.LOCK_FAILURE;
	}

	@Override
	protected Result doLink(String target) throws IOException {
		final SymbolicRef newRef = new SymbolicRef(
				dstRef.getName()
				new ObjectIdRef.Unpeeled(
						Storage.NEW
						target
						null));
		if (getRefDatabase().compareAndPut(dstRef
			getRefDatabase().stored(newRef);
			if (dstRef.getStorage() == Ref.Storage.NEW)
				return Result.NEW;
			return Result.FORCED;
		}
		return Result.LOCK_FAILURE;
	}
}
