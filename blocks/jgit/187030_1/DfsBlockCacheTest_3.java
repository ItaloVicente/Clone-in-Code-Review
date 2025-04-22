	@SuppressWarnings("resource")
	@Test
	public void hasCacheHotMap() throws Exception {
		Map<PackExt
		cacheHotMap.put(PackExt.INDEX
		DfsBlockCache.reconfigure(new DfsBlockCacheConfig().setBlockSize(512)
				.setBlockLimit(512 * 4).setCacheHotMap(cacheHotMap));
		cache = DfsBlockCache.getInstance();

		DfsRepositoryDescription repo = new DfsRepositoryDescription("test");
		InMemoryRepository r1 = new InMemoryRepository(repo);
		byte[] content = rng.nextBytes(424242);
		ObjectId id;
		try (ObjectInserter ins = r1.newObjectInserter()) {
			id = ins.insert(OBJ_BLOB
			ins.flush();
		}

		try (ObjectReader rdr = r1.newObjectReader()) {
			byte[] actual = rdr.open(id
			assertTrue(Arrays.equals(content
		}
		assertTrue(LongStream.of(cache.getHitCount()).sum() > 0);
		assertEquals(99

		InMemoryRepository r2 = new InMemoryRepository(repo);
		content = rng.nextBytes(424242);
		try (ObjectInserter ins = r2.newObjectInserter()) {
			ins.insert(OBJ_BLOB
			ins.flush();
		}
		assertEquals(0
		assertTrue(cache.getEvictions()[PackExt.PACK.getPosition()] > 0);
		assertEquals(0
	}

	@SuppressWarnings("resource")
	@Test
	public void noConcurrencySerializedReads() throws Exception {
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

