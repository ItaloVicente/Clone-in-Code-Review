
package org.eclipse.jgit.lib;

import java.io.IOException;

class RefDirectoryUpdate extends RefUpdate {
	private final RefDirectory database;

	private LockFile lock;

	RefDirectoryUpdate(final RefDirectory r
		super(ref);
		database = r;
	}

	@Override
	protected RefDirectory getRefDatabase() {
		return database;
	}

	@Override
	protected Repository getRepository() {
		return database.getRepository();
	}

	@Override
	protected boolean tryLock() throws IOException {
		Ref dst = getRef().getLeaf();
		String name = dst.getName();
		lock = new LockFile(database.fileFor(name));
		if (lock.lock()) {
			dst = database.getRef(name);
			setOldObjectId(dst != null ? dst.getObjectId() : null);
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void unlock() {
		if (lock != null) {
			lock.unlock();
			lock = null;
		}
	}

	@Override
	protected Result doUpdate(final Result status) throws IOException {
		lock.setNeedStatInformation(true);
		lock.write(getNewObjectId());

		String msg = getRefLogMessage();
		if (msg != null) {
			if (isRefLogIncludingResult()) {
				String strResult = toResultString(status);
				if (strResult != null) {
					if (msg.length() > 0)
						msg = msg + ": " + strResult;
					else
						msg = strResult;
				}
			}
			database.log(this
		}
		if (!lock.commit())
			return Result.LOCK_FAILURE;
		database.stored(this
		return status;
	}

	private String toResultString(final Result status) {
		switch (status) {
		case FORCED:
			return "forced-update";
		case FAST_FORWARD:
			return "fast forward";
		case NEW:
			return "created";
		default:
			return null;
		}
	}

	@Override
	protected Result doDelete(final Result status) throws IOException {
		if (getRef().getLeaf().getStorage() != Ref.Storage.NEW)
			database.delete(this);
		return status;
	}
}
