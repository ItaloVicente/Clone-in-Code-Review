		DfsReader reader = (DfsReader) r1.newObjectReader();
		ExecutorService pool = Executors.newFixedThreadPool(10);
		for (DfsPackFile pack : r1.getObjectDatabase().getPacks()) {
			if (pack.isGarbage()) {
				continue;
			}
			asyncRun(pool
			asyncRun(pool
			asyncRun(pool
