		lock.lock();
		try {
			pm.update(completed);
		} finally {
			lock.unlock();
		}
