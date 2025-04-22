		snapshotLock.lock();
		try {
			snapshot = FileSnapshot.save(getIndexFile());
		} finally {
			snapshotLock.unlock();
		}
