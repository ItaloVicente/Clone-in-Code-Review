		lock.lock();
		try {
			snapshot = FileSnapshot.save(getIndexFile());
		} finally {
			lock.unlock();
		}
