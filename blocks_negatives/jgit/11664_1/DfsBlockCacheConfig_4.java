
		setReadAheadLimit(rc.getInt(
				CONFIG_CORE_SECTION,
				CONFIG_DFS_SECTION,
				CONFIG_KEY_READ_AHEAD_LIMIT,
				getReadAheadLimit()));

		int readAheadThreads = rc.getInt(
				CONFIG_CORE_SECTION,
				CONFIG_DFS_SECTION,
				CONFIG_KEY_READ_AHEAD_THREADS,
				0);

		if (0 < getReadAheadLimit() && 0 < readAheadThreads) {
			setReadAheadService(new ThreadPoolExecutor(
					1, // Minimum number of threads kept alive.
					readAheadThreads, // Maximum threads active.
					60, TimeUnit.SECONDS, // Idle threads wait this long before ending.
					new ArrayBlockingQueue<Runnable>(1), // Do not queue deeply.
					new ThreadFactory() {
						private final AtomicInteger cnt = new AtomicInteger();
						private final ThreadGroup group = new ThreadGroup(name);

						public Thread newThread(Runnable body) {
							int id = cnt.incrementAndGet();
							Thread thread = new Thread(group, body, name + "-" + id); //$NON-NLS-1$
							thread.setDaemon(true);
							thread.setContextClassLoader(getClass().getClassLoader());
							return thread;
						}
					}, ReadAheadRejectedExecutionHandler.INSTANCE));
		}
