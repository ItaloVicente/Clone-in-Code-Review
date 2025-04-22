
package org.eclipse.jgit.internal.storage.reftree;

import static org.eclipse.jgit.lib.Ref.Storage.LOOSE;
import static org.eclipse.jgit.lib.Ref.Storage.NEW;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.SymbolicRef;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.ReceiveCommand;

class RefTreeUpdate extends RefUpdate {
	private final RefTreeDatabase refdb;
	private Ref oldRef;
	private RefTreeBatch batch;

	RefTreeUpdate(RefTreeDatabase refdb
		super(ref);
		this.refdb = refdb;
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
		batch = new RefTreeBatch(refdb);
		try (RevWalk rw = new RevWalk(getRepository())) {
			batch.init(rw);
			oldRef = batch.exactRef(rw.getObjectReader()
			if (oldRef != null && oldRef.getObjectId() != null) {
				setOldObjectId(oldRef.getObjectId());
			} else if (oldRef == null && getExpectedOldObjectId() != null) {
				setOldObjectId(ObjectId.zeroId());
			}
		}
		return true;
	}

	@Override
	protected void unlock() {
	}

	@Override
	protected Result doUpdate(Result desiredResult) throws IOException {
		return run(newRef(getName()
	}

	private Ref newRef(String name
			throws MissingObjectException
		try (RevWalk rw = new RevWalk(getRepository())) {
			RevObject o = rw.parseAny(id);
			if (o instanceof RevTag) {
				RevObject p = rw.peel(o);
				return new ObjectIdRef.PeeledTag(LOOSE
			}
			return new ObjectIdRef.PeeledNonTag(LOOSE
		}
	}

	@Override
	protected Result doDelete(Result desiredResult) throws IOException {
		return run(null
	}

	@Override
	protected Result doLink(String target) throws IOException {
		Ref dst = new ObjectIdRef.Unpeeled(NEW
		SymbolicRef n = new SymbolicRef(getName()
		Result desiredResult = getRef().getStorage() == NEW
			? Result.NEW
			: Result.FORCED;
		return run(n
	}

	private Result run(@Nullable Ref newRef
			throws IOException {
		Command c = new Command(oldRef
		try (RevWalk rw = new RevWalk(getRepository())) {
			batch.setRefLogIdent(getRefLogIdent());
			batch.setRefLogMessage(
					getRefLogMessage()
					isRefLogIncludingResult());
			batch.execute(rw
		}
		return translate(c.getResult()
	}

	static Result translate(ReceiveCommand.Result r
		switch (r) {
		case OK:
			return desiredResult;

		case LOCK_FAILURE:
			return Result.LOCK_FAILURE;

		case NOT_ATTEMPTED:
			return Result.NOT_ATTEMPTED;

		case REJECTED_MISSING_OBJECT:
			return Result.IO_FAILURE;

		case REJECTED_CURRENT_BRANCH:
			return Result.REJECTED_CURRENT_BRANCH;

		case REJECTED_OTHER_REASON:
		case REJECTED_NOCREATE:
		case REJECTED_NODELETE:
		case REJECTED_NONFASTFORWARD:
		default:
			return Result.REJECTED;
		}
	}
}
