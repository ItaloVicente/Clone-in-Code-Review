	public void waitForJobs(long maxWaitTime, Object family)
			throws InterruptedException {
		waitForJobs(maxWaitTime, 50, family);
	}

	public void waitForJobs(long maxWaitTime, long minWaitTime, Object family)
			throws InterruptedException {
		Thread.sleep(minWaitTime);
		long start = System.currentTimeMillis();
		IJobManager jobManager = Job.getJobManager();

		Job[] jobs = jobManager.find(family);
		while (jobs.length > 0) {
			Thread.sleep(100);
			jobs = jobManager.find(family);
			if (System.currentTimeMillis() - start > maxWaitTime) {
				return;
			}
		}
	}

