		} else if (executor == null) {
			ExecutorService pool = Executors.newFixedThreadPool(threads);
			try {
				runTasks(pool
			} finally {
				pool.shutdown();
				for (;;) {
