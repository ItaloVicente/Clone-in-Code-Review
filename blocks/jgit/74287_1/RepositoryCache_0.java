	static void reconfigure(RepositoryCacheConfig repositoryCacheConfig) {
		cache.expireAfter = repositoryCacheConfig.getExpireAfter();
		synchronized (cache.scheduler) {
			if (cache.cleanupTask != null) {
				cache.cleanupTask.cancel(false);
			}
			long newDelay = repositoryCacheConfig.getCleanupDelay();
			cache.cleanupTask = cache.scheduler
					.scheduleWithFixedDelay(new Runnable() {
						@Override
						public void run() {
							try {
								for (Reference<Repository> ref : cache.cacheMap
										.values()) {
									Repository repository = ref.get();
									if (repository != null) {
										cache.evict(repository);
									}
								}
							} catch (Throwable e) {
								LOG.error(e.getMessage()
							}
						}
					}
		}
	}

