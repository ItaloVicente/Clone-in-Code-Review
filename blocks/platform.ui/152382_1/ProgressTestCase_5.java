
	public static void joinJobs(Iterable<? extends Job> jobs, int timeout, TimeUnit timeoutUnit)
			throws OperationCanceledException, InterruptedException, TimeoutException {
		long timeoutMs = timeoutUnit.toMillis(timeout);
		long start = System.currentTimeMillis();
		for (Job job : jobs) {
			while (!job.join(100, null)) {
				if (System.currentTimeMillis() - start > timeoutMs) {
					throw new TimeoutException();
				}
				processEvents();
			}
		}
	}
