	private void configureEviction(
			RepositoryCacheConfig repositoryCacheConfig) {
		expireAfter = repositoryCacheConfig.getExpireAfter();
		ScheduledThreadPoolExecutor scheduler = WorkQueue.getExecutor();
		synchronized (scheduler) {
			if (cleanupTask != null) {
				cleanupTask.cancel(false);
			}
			long delay = repositoryCacheConfig.getCleanupDelay();
			cleanupTask = scheduler.scheduleWithFixedDelay(new Runnable() {
