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
	public void noConcurrencySerializedReads_oneRepo() throws Exception {
		InMemoryRepository r1 = createRepoWithBitmap("test");
		resetCache(1);

		DfsReader reader = (DfsReader) r1.newObjectReader();
		for (DfsPackFile pack : r1.getObjectDatabase().getPacks()) {
			if (pack.isGarbage()) {
				continue;
			}
			asyncRun(() -> pack.getBitmapIndex(reader));
			asyncRun(() -> pack.getPackIndex(reader));
			asyncRun(() -> pack.getBitmapIndex(reader));
		}
		waitForExecutorPoolTermination();

		assertEquals(1
		assertEquals(1
		assertEquals(1
	}

	@SuppressWarnings("resource")
	@Test
	public void noConcurrencySerializedReads_twoRepos() throws Exception {
		InMemoryRepository r1 = createRepoWithBitmap("test1");
		InMemoryRepository r2 = createRepoWithBitmap("test2");
		resetCache(1);

		DfsReader reader = (DfsReader) r1.newObjectReader();
		DfsPackFile[] r1Packs = r1.getObjectDatabase().getPacks();
		DfsPackFile[] r2Packs = r2.getObjectDatabase().getPacks();
		assertEquals(r1Packs.length

		for (int i = 0; i < r1.getObjectDatabase().getPacks().length; ++i) {
			DfsPackFile pack1 = r1Packs[i];
			DfsPackFile pack2 = r2Packs[i];
			if (pack1.isGarbage() || pack2.isGarbage()) {
				continue;
			}
			asyncRun(() -> pack1.getBitmapIndex(reader));
			asyncRun(() -> pack2.getBitmapIndex(reader));
		}

		waitForExecutorPoolTermination();
		assertEquals(2
		assertEquals(2
		assertEquals(2
	}

	@SuppressWarnings("resource")
	@Test
	public void lowConcurrencyParallelReads_twoRepos() throws Exception {
		InMemoryRepository r1 = createRepoWithBitmap("test1");
		InMemoryRepository r2 = createRepoWithBitmap("test2");
		resetCache(2);

		DfsReader reader = (DfsReader) r1.newObjectReader();
		DfsPackFile[] r1Packs = r1.getObjectDatabase().getPacks();
		DfsPackFile[] r2Packs = r2.getObjectDatabase().getPacks();
		assertEquals(r1Packs.length

		for (int i = 0; i < r1.getObjectDatabase().getPacks().length; ++i) {
			DfsPackFile pack1 = r1Packs[i];
			DfsPackFile pack2 = r2Packs[i];
			if (pack1.isGarbage() || pack2.isGarbage()) {
				continue;
			}
			asyncRun(() -> pack1.getBitmapIndex(reader));
			asyncRun(() -> pack2.getBitmapIndex(reader));
		}

		waitForExecutorPoolTermination();
		assertEquals(2
		assertEquals(2
		assertEquals(2
	}

	@SuppressWarnings("resource")
	@Test
	public void lowConcurrencyParallelReads_twoReposAndIndex()
			throws Exception {
		InMemoryRepository r1 = createRepoWithBitmap("test1");
		InMemoryRepository r2 = createRepoWithBitmap("test2");
		resetCache(2);

		DfsReader reader = (DfsReader) r1.newObjectReader();
		DfsPackFile[] r1Packs = r1.getObjectDatabase().getPacks();
		DfsPackFile[] r2Packs = r2.getObjectDatabase().getPacks();
		assertEquals(r1Packs.length

		for (int i = 0; i < r1.getObjectDatabase().getPacks().length; ++i) {
			DfsPackFile pack1 = r1Packs[i];
			DfsPackFile pack2 = r2Packs[i];
			if (pack1.isGarbage() || pack2.isGarbage()) {
				continue;
			}
			asyncRun(() -> pack1.getBitmapIndex(reader));
			asyncRun(() -> pack1.getPackIndex(reader));
			asyncRun(() -> pack2.getBitmapIndex(reader));
		}
		waitForExecutorPoolTermination();

		assertEquals(2
		assertEquals(2
		assertEquals(2
	}

	@SuppressWarnings("resource")
	@Test
	public void highConcurrencyParallelReads_oneRepo() throws Exception {
		InMemoryRepository r1 = createRepoWithBitmap("test");
		resetCache();

		DfsReader reader = (DfsReader) r1.newObjectReader();
		for (DfsPackFile pack : r1.getObjectDatabase().getPacks()) {
			if (pack.isGarbage()) {
				continue;
			}
			asyncRun(() -> pack.getBitmapIndex(reader));
			asyncRun(() -> pack.getPackIndex(reader));
			asyncRun(() -> pack.getBitmapIndex(reader));
		}
		waitForExecutorPoolTermination();

		assertEquals(1
		assertEquals(1
		assertEquals(1
	}

