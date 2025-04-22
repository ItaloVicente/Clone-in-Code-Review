		lock.lock();
		try {
			if (snapshot == null) {
				snapshot = FileSnapshot.save(indexFile);
			} else if (snapshot.isModified(indexFile)) {
				notifyIndexChanged(false);
			}
		} finally {
			if (lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
