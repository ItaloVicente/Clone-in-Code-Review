	public void endTask() {
		lock.lock();
		try {
			pm.endTask();
		} finally {
			lock.unlock();
		}
