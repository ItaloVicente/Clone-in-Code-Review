
package org.eclipse.jgit.internal.storage.reftree;

import java.io.IOException;

import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;

class FailUpdate extends RefUpdate {
	private final RefTreeDatabase refdb;

	FailUpdate(RefTreeDatabase refdb
		super(new ObjectIdRef.Unpeeled(Ref.Storage.NEW
		this.refdb = refdb;
		setCheckConflicting(false);
	}

	@Override
	protected RefDatabase getRefDatabase() {
		return refdb;
	}

	@Override
	protected Repository getRepository() {
		return refdb.getRepository();
	}

	@Override
	protected boolean tryLock(boolean deref) throws IOException {
		return false;
	}

	@Override
	protected void unlock() {
	}

	@Override
	protected Result doUpdate(Result desiredResult) throws IOException {
		return Result.LOCK_FAILURE;
	}

	@Override
	protected Result doDelete(Result desiredResult) throws IOException {
		return Result.LOCK_FAILURE;
	}

	@Override
	protected Result doLink(String target) throws IOException {
		return Result.LOCK_FAILURE;
	}
}
