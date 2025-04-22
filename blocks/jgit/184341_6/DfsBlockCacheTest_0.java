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

