
		Runnable terminator = new Runnable() {
			@Override
			public void run() {
				try {
					for (Reference<Repository> ref : cache.cacheMap.values()) {
						Repository repository = ref.get();
						if (repository != null) {
							cache.evict(repository);
						}
					}
				} catch (Throwable e) {
					LOG.error(e.getMessage()
				}
			}
		};
		scheduler = new ScheduledThreadPoolExecutor(1
			private final ThreadFactory baseFactory = Executors
					.defaultThreadFactory();

			public Thread newThread(Runnable taskBody) {
				Thread thr = baseFactory.newThread(taskBody);
				thr.setDaemon(true);
				return thr;
			}
		});
		schedulerKiller = new Object() {
			@Override
			protected void finalize() throws Throwable {
				scheduler.shutdownNow();
			}
		};

		scheduler.scheduleWithFixedDelay(terminator
		scheduler.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
		scheduler.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
		scheduler.prestartAllCoreThreads();

		scheduler.setThreadFactory(Executors.defaultThreadFactory());
