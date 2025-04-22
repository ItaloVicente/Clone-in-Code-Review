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
	}

