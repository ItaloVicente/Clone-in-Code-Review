		if (!ref.exists() || deleteRef()) {
			if (renameLock()) {
				haveLck = false;
				return true;
			}
		}
		unlock();
		return false;
	}

	private boolean deleteRef() {
		if (!fs.retryFailedLockFileCommit())
			return ref.delete();

		for (int attempts = 0; attempts < 10; attempts++) {
			if (ref.delete())
				return true;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				return false;
			}
		}
		return false;
	}

	private boolean renameLock() {
		if (!fs.retryFailedLockFileCommit())
			return lck.renameTo(ref);

		for (int attempts = 0; attempts < 10; attempts++) {
			if (lck.renameTo(ref))
				return true;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				return false;
			}
		}
		return false;
