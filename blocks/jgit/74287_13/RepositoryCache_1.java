			long delay = repositoryCacheConfig.getCleanupDelay();
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
