
		Runnable terminator = new Runnable() {
			@Override
			public void run() {
				try {
					for (Reference<Repository> ref : cacheMap.values()) {
						Repository repository = ref.get();
						if (repository != null) {
							repository.evict(20000);
						}
					}
				} catch (Throwable e) {
					LOG.error(e.getMessage()
				}
			}
		};
		ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(
				1);
		scheduler.scheduleWithFixedDelay(terminator
