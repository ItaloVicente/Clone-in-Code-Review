	private boolean deleteRef() {
		if (!fs.retryFailedLockFileCommit())
			return ref.delete();
		for (int i = 0; i < 10; i++) {
			if (ref.delete()) {
				return true;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		return false;
	}

