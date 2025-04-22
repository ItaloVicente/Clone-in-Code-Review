		gcExecutor = Executors.newSingleThreadExecutor(new ThreadFactory() {
			private final ThreadFactory baseFactory = Executors
					.defaultThreadFactory();

			@Override
			public Thread newThread(Runnable taskBody) {
				Thread thr = baseFactory.newThread(taskBody);
				return thr;
			}
		});
