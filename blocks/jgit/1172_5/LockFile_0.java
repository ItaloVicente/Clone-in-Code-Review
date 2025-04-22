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

