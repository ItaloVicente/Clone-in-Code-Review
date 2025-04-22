	@SuppressWarnings("resource")
	@Test
	public void concurrencyLevel1ParallelLoad() throws Exception {
		DfsRepositoryDescription repo = new DfsRepositoryDescription("test");
		InMemoryRepository r1 = new InMemoryRepository(repo);
		TestRepository<InMemoryRepository> repository = new TestRepository<>(
				r1);
		RevCommit commit = repository.branch("/refs/ref1").commit()
				.add("blob1"
		repository.branch("/refs/ref2").commit().add("blob2"
				.parent(commit).create();

		new DfsGarbageCollector(r1).pack(null);
		DfsBlockCache.reconfigure(new DfsBlockCacheConfig().setBlockSize(512)
				.setBlockLimit(1 << 20).setConcurrencyLevel(1));
		cache = DfsBlockCache.getInstance();

		DfsReader reader = (DfsReader) r1.newObjectReader();
		ExecutorService pool = Executors.newFixedThreadPool(10);
		for (DfsPackFile pack : r1.getObjectDatabase().getPacks()) {
			if (pack.isGarbage()) {
				continue;
			}
			asyncRun(pool
			asyncRun(pool
			asyncRun(pool
		}

		pool.shutdown();
		pool.awaitTermination(500
		assertTrue("Threads did not complete
				pool.isTerminated());
		assertEquals(1
		assertEquals(1
	}

