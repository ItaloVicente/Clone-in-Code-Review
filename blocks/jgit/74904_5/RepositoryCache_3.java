		}
		configureEviction(new RepositoryCacheConfig());
	}

	private void configureEviction(
			RepositoryCacheConfig repositoryCacheConfig) {
		expireAfter = repositoryCacheConfig.getExpireAfter();
		ScheduledThreadPoolExecutor scheduler = WorkQueue.getExecutor();
		synchronized (scheduler) {
			if (cleanupTask != null) {
				cleanupTask.cancel(false);
			}
			long delay = repositoryCacheConfig.getCleanupDelay();
			if (delay == RepositoryCacheConfig.NO_CLEANUP) {
				return;
			}
			cleanupTask = scheduler.scheduleWithFixedDelay(new Runnable() {
				@Override
				public void run() {
					try {
						cache.clearAllExpired();
					} catch (Throwable e) {
						LOG.error(e.getMessage()
					}
				}
			}
		}
