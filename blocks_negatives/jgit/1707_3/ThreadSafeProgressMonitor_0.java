		lock.lock();
		try {
			pm.start(totalTasks);
		} finally {
			lock.unlock();
		}
