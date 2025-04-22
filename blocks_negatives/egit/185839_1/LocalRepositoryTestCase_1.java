	/**
	 *  This method should only be used in exceptional cases.
	 *  Try to avoid using it e.g. by joining execution jobs
	 *  instead of waiting a given amount of time {@link TestUtil#joinJobs(Object)}
	 * @throws InterruptedException
	 */
	protected static void waitInUI() throws InterruptedException {
		TestUtil.processUIEvents(1000);
	}

