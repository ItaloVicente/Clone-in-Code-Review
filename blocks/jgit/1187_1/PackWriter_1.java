		final List<Throwable> errors = Collections
				.synchronizedList(new ArrayList<Throwable>());
		if (executor instanceof ExecutorService) {
			runTasks((ExecutorService) executor

		} else if (executor == null) {
			ExecutorService pool = Executors.newFixedThreadPool(threads);
			try {
				runTasks(pool
			} finally {
				pool.shutdown();
				for (;;) {
