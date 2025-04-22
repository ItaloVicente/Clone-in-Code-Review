		if (!isMainThread())
			throw new IllegalStateException();
		pm.beginTask(title
	}

	public void startWorker() {
		startWorkers(1);
	}

	public void startWorkers(int count) {
		workers.addAndGet(count);
	}

	public void endWorker() {
		if (workers.decrementAndGet() == 0)
			process.release();
	}

	public void pollForUpdates() {
		assert isMainThread();
		doUpdates();
	}

	public void waitForCompletion() throws InterruptedException {
		assert isMainThread();
		while (0 < workers.get()) {
			doUpdates();
			process.acquire();
