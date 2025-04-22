
package org.eclipse.jgit.lib;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.PushCertificate;

public abstract class RefUpdate {
	public static enum Result {
		NOT_ATTEMPTED

		LOCK_FAILURE

		NO_CHANGE

		NEW

		FORCED

		FAST_FORWARD

		REJECTED

		REJECTED_CURRENT_BRANCH

		IO_FAILURE

		RENAMED

		REJECTED_MISSING_OBJECT

		REJECTED_OTHER_REASON;
	}

	private ObjectId newValue;

	private boolean force;

	private PersonIdent refLogIdent;

	private String refLogMessage;

	private boolean refLogIncludeResult;

	private boolean forceRefLog;

	private ObjectId oldValue;

	private ObjectId expValue;

	private Result result = Result.NOT_ATTEMPTED;

	private PushCertificate pushCert;

	private final Ref ref;

	private boolean detachingSymbolicRef;

	private boolean checkConflicting = true;

	protected RefUpdate(Ref ref) {
		this.ref = ref;
		oldValue = ref.getObjectId();
	}

	protected abstract RefDatabase getRefDatabase();

	protected abstract Repository getRepository();

	protected abstract boolean tryLock(boolean deref) throws IOException;

	protected abstract void unlock();

	protected abstract Result doUpdate(Result desiredResult) throws IOException;

	protected abstract Result doDelete(Result desiredResult) throws IOException;

	protected abstract Result doLink(String target) throws IOException;

	public String getName() {
		return getRef().getName();
	}

	public Ref getRef() {
		return ref;
	}

	public ObjectId getNewObjectId() {
		return newValue;
	}

	public void setDetachingSymbolicRef() {
		detachingSymbolicRef = true;
	}

	public boolean isDetachingSymbolicRef() {
		return detachingSymbolicRef;
	}

	public void setNewObjectId(AnyObjectId id) {
		newValue = id.copy();
	}

	public ObjectId getExpectedOldObjectId() {
		return expValue;
	}

	public void setExpectedOldObjectId(AnyObjectId id) {
		expValue = id != null ? id.toObjectId() : null;
	}

	public boolean isForceUpdate() {
		return force;
	}

	public void setForceUpdate(boolean b) {
		force = b;
	}

	public PersonIdent getRefLogIdent() {
		return refLogIdent;
	}

	public void setRefLogIdent(PersonIdent pi) {
		refLogIdent = pi;
	}

	public String getRefLogMessage() {
		return refLogMessage;
	}

	protected boolean isRefLogIncludingResult() {
		return refLogIncludeResult;
	}

	public void setRefLogMessage(String msg
		if (msg == null && !appendStatus)
			disableRefLog();
		else if (msg == null && appendStatus) {
			refLogIncludeResult = true;
		} else {
			refLogMessage = msg;
			refLogIncludeResult = appendStatus;
		}
	}

	public void disableRefLog() {
		refLogMessage = null;
		refLogIncludeResult = false;
	}

	public void setForceRefLog(boolean force) {
		forceRefLog = force;
	}

	protected boolean isForceRefLog() {
		return forceRefLog;
	}

	public ObjectId getOldObjectId() {
		return oldValue;
	}

	protected void setOldObjectId(ObjectId old) {
		oldValue = old;
	}

	public void setPushCertificate(PushCertificate cert) {
		pushCert = cert;
	}

	protected PushCertificate getPushCertificate() {
		return pushCert;
	}

	public Result getResult() {
		return result;
	}

	private void requireCanDoUpdate() {
		if (newValue == null)
			throw new IllegalStateException(JGitText.get().aNewObjectIdIsRequired);
	}

	public Result forceUpdate() throws IOException {
		force = true;
		return update();
	}

	public Result update() throws IOException {
		try (RevWalk rw = new RevWalk(getRepository())) {
			rw.setRetainBody(false);
			return update(rw);
		}
	}

	public Result update(RevWalk walk) throws IOException {
		requireCanDoUpdate();
		try {
			return result = updateImpl(walk
				@Override
				Result execute(Result status) throws IOException {
					if (status == Result.NO_CHANGE)
						return status;
					return doUpdate(status);
				}
			});
		} catch (IOException x) {
			result = Result.IO_FAILURE;
			throw x;
		}
	}

	public Result delete() throws IOException {
		try (RevWalk rw = new RevWalk(getRepository())) {
			rw.setRetainBody(false);
			return delete(rw);
		}
	}

	public Result delete(RevWalk walk) throws IOException {
		final String myName = detachingSymbolicRef
				? getRef().getName()
				: getRef().getLeaf().getName();
		if (myName.startsWith(Constants.R_HEADS) && !getRepository().isBare()) {
			Ref head = getRefDatabase().exactRef(Constants.HEAD);
			while (head != null && head.isSymbolic()) {
				head = head.getTarget();
				if (myName.equals(head.getName()))
					return result = Result.REJECTED_CURRENT_BRANCH;
			}
		}

		try {
			return result = updateImpl(walk
				@Override
				Result execute(Result status) throws IOException {
					return doDelete(status);
				}
			});
		} catch (IOException x) {
			result = Result.IO_FAILURE;
			throw x;
		}
	}

	public Result link(String target) throws IOException {
		if (!target.startsWith(Constants.R_REFS))
			throw new IllegalArgumentException(MessageFormat.format(JGitText.get().illegalArgumentNotA
		if (checkConflicting && getRefDatabase().isNameConflicting(getName()))
			return Result.LOCK_FAILURE;
		try {
			if (!tryLock(false))
				return Result.LOCK_FAILURE;

			final Ref old = getRefDatabase().exactRef(getName());
			if (old != null && old.isSymbolic()) {
				final Ref dst = old.getTarget();
				if (target.equals(dst.getName()))
					return result = Result.NO_CHANGE;
			}

			if (old != null && old.getObjectId() != null)
				setOldObjectId(old.getObjectId());

			final Ref dst = getRefDatabase().exactRef(target);
			if (dst != null && dst.getObjectId() != null)
				setNewObjectId(dst.getObjectId());

			return result = doLink(target);
		} catch (IOException x) {
			result = Result.IO_FAILURE;
			throw x;
		} finally {
			unlock();
		}
	}

	private Result updateImpl(RevWalk walk
			throws IOException {
		RevObject newObj;
		RevObject oldObj;

		if (oldValue == null && checkConflicting
				&& getRefDatabase().isNameConflicting(getName())) {
			return Result.LOCK_FAILURE;
		}
		try {
			if (!tryLock(!detachingSymbolicRef)) {
				return Result.LOCK_FAILURE;
			}
			if (expValue != null) {
				final ObjectId o;
				o = oldValue != null ? oldValue : ObjectId.zeroId();
				if (!AnyObjectId.equals(expValue
					return Result.LOCK_FAILURE;
				}
			}
			try {
				newObj = safeParseNew(walk
			} catch (MissingObjectException e) {
				return Result.REJECTED_MISSING_OBJECT;
			}

			if (oldValue == null) {
				return store.execute(Result.NEW);
			}

			oldObj = safeParseOld(walk
			if (newObj == oldObj && !detachingSymbolicRef) {
				return store.execute(Result.NO_CHANGE);
			}

			if (isForceUpdate()) {
				return store.execute(Result.FORCED);
			}

			if (newObj instanceof RevCommit && oldObj instanceof RevCommit) {
				if (walk.isMergedInto((RevCommit) oldObj
					return store.execute(Result.FAST_FORWARD);
				}
			}

			return Result.REJECTED;
		} finally {
			unlock();
		}
	}

	public void setCheckConflicting(boolean check) {
		checkConflicting = check;
	}

	private static RevObject safeParseNew(RevWalk rw
			throws IOException {
		if (newId == null || ObjectId.zeroId().equals(newId)) {
			return null;
		}
		return rw.parseAny(newId);
	}

	private static RevObject safeParseOld(RevWalk rw
			throws IOException {
		try {
			return oldId != null ? rw.parseAny(oldId) : null;
		} catch (MissingObjectException e) {
			return null;
		}
	}

	private static abstract class Store {
		abstract Result execute(Result status) throws IOException;
	}
}
