	public static void waitForJobs(long minTimeMs, long maxTimeMs) {
		if (maxTimeMs < minTimeMs) {
			throw new IllegalArgumentException(
					"Max time is smaller as min time!");
		}
		final long start = System.currentTimeMillis();
		while (System.currentTimeMillis() - start < minTimeMs) {
			processUIEvents();
		}
		while (!Job.getJobManager().isIdle()
				&& System.currentTimeMillis() - start < maxTimeMs) {
			processUIEvents();
		}
	}

