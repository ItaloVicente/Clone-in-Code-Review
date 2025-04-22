		configureEviction(new RepositoryCacheConfig());
	}

	private void configureEviction(
			RepositoryCacheConfig repositoryCacheConfig) {
		expireAfter = repositoryCacheConfig.getExpireAfter();
		synchronized (scheduler) {
			if (cleanupTask != null) {
				cleanupTask.cancel(false);
			}
			long newDelay = repositoryCacheConfig.getCleanupDelay();
			cleanupTask = scheduler.scheduleWithFixedDelay(new Runnable() {
				@Override
				public void run() {
					try {
						for (Reference<Repository> ref : cache.cacheMap
								.values()) {
							Repository repository = ref.get();
							if (repository != null) {
								if (repository.useCnt.get() == 0
										&& (System.currentTimeMillis()
												- repository.closedAt
														.get() > expireAfter)) {
									RepositoryCache.close(repository);
								}
							}
						}
					} catch (Throwable e) {
						LOG.error(e.getMessage()
					}
				}
			}
		}
