		if (!isMainThread())
			throw new IllegalStateException();
		pm.endTask();
	}

	private boolean isMainThread() {
		return Thread.currentThread() == mainThread;
