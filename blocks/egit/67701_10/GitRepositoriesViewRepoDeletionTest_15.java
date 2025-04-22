	@SuppressWarnings("restriction")
	private void waitForDecorations() throws InterruptedException {
		TestUtil.joinJobs(
				org.eclipse.ui.internal.decorators.DecoratorManager.FAMILY_DECORATE);
	}

	private void waitForFinalization(int maxMillis) {
		long stop = System.currentTimeMillis() + maxMillis;
		MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
		do {
			System.gc();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
		} while (System.currentTimeMillis() < stop
				&& memoryBean.getObjectPendingFinalizationCount() > 0);
	}
