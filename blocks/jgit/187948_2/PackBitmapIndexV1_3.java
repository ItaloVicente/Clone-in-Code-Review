	private static final ExecutorService executor = Executors
			.newCachedThreadPool(new ThreadFactory() {
				private final ThreadFactory baseFactory = Executors
						.defaultThreadFactory();

				private final AtomicInteger threadNumber = new AtomicInteger(0);

				@Override
				public Thread newThread(Runnable runnable) {
					Thread thread = baseFactory.newThread(runnable);
							+ threadNumber.getAndIncrement());
					thread.setDaemon(true);
					return thread;
				}
			});

