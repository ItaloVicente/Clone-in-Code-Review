		snapshotLock.lock();
		try {
			if (snapshot == null) {
				snapshot = FileSnapshot.save(indexFile);
			} else if (snapshot.isModified(indexFile)) {
				notifyIndexChanged(false);
			}
		} finally {
			if (snapshotLock.isHeldByCurrentThread()) {
				snapshotLock.unlock();
			}
