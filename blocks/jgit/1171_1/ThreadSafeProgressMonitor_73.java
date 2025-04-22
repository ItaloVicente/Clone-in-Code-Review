	public ThreadSafeProgressMonitor(ProgressMonitor pm) {
		this.pm = pm;
		this.lock = new ReentrantLock();
	}

	public void start(int totalTasks) {
		lock.lock();
		try {
			pm.start(totalTasks);
		} finally {
			lock.unlock();
		}
	}
