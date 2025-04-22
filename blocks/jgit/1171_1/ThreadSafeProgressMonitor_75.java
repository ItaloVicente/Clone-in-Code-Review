	public void update(int completed) {
		lock.lock();
		try {
			pm.update(completed);
		} finally {
			lock.unlock();
		}
