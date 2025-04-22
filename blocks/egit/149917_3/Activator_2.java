	public void waitForResourceRefresh(long timeoutMillis) {
		try {
			refreshJob.join(timeoutMillis, null);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

