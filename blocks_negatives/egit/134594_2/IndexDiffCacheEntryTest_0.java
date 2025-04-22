	/**
	 * Waits at least 50 milliseconds until no jobs of given family are running
	 *
	 * @param maxWaitTime
	 * @param family
	 * @throws InterruptedException
	 */
	private void waitForJobs(long maxWaitTime, Object family)
			throws InterruptedException {
		TestUtils.waitForJobs(maxWaitTime, family);
	}

