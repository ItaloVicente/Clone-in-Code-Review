	private static final ScheduledThreadPoolExecutor alarmQueue;

	static final Object alarmQueueKiller;

	static {
		int threads = 1;
		alarmQueue = new ScheduledThreadPoolExecutor(threads,
				new ThreadFactory() {
					private final ThreadFactory baseFactory = Executors
							.defaultThreadFactory();

					public Thread newThread(Runnable taskBody) {
						Thread thr = baseFactory.newThread(taskBody);
						thr.setDaemon(true);
						return thr;
					}
				});
		alarmQueue.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
		alarmQueue.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
		alarmQueue.prestartAllCoreThreads();

		alarmQueue.setThreadFactory(Executors.defaultThreadFactory());

		alarmQueueKiller = new Object() {
			@Override
			protected void finalize() {
				alarmQueue.shutdownNow();
			}
		};
	}

