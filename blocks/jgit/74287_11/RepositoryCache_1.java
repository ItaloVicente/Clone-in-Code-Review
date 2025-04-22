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

				@Override
				public void run() {
					try {
						for (Reference<Repository> ref : cache.cacheMap
								.values()) {
							Repository db = ref.get();
							if (db != null && isExpired(db)) {
								RepositoryCache.close(db);
							}
