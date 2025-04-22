	public void waitForJobs(long minTimeMs, long maxTimeMs) {
		if (maxTimeMs < minTimeMs) {
			throw new IllegalArgumentException("Max time is smaller as min time!");
		}
		final long start = System.currentTimeMillis();
		while (System.currentTimeMillis() - start < minTimeMs) {
			processEvents();
			sleep(10);
		}
		while (!Job.getJobManager().isIdle() && System.currentTimeMillis() - start < maxTimeMs) {
			processEvents();
			sleep(10);
		}
	}
