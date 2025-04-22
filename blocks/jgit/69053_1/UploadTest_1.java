
	@Test
	public void testParallelUploads() throws Exception {
		int count = 10;
		List<Path> paths = new ArrayList<>(count);

		for (int i = 0; i < count; i++) {
			Path f = Paths.get(getTempDirectory().toString()
					"largeRandomFile_" + i);
			createPseudoRandomContentFile(f
			paths.add(f);
		}

		final CyclicBarrier barrier = new CyclicBarrier(count);

		ExecutorService e = Executors.newFixedThreadPool(count);
		try {
			for (final Path p : paths) {
				e.submit(new Callable<Void>() {
					@Override
					public Void call() throws Exception {
						barrier.await();
						putContent(p);
						return null;
					}
				});
			}
		} finally {
			e.shutdown();
			e.awaitTermination(Long.MAX_VALUE
		}
	}
