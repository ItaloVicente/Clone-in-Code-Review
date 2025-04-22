		Thread.sleep(50);
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
