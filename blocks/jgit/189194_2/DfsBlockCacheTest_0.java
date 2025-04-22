	@SuppressWarnings("resource")
	@Test
	public void hasIndexEventConsumerOnlyLoaded() throws Exception {
		AtomicInteger loaded = new AtomicInteger();
		IndexEventConsumer indexEventConsumer = new IndexEventConsumer() {
			@Override
			public void acceptLoadedEvent(int packExtPos
					long loadMicros
					Duration lastEvictionDuration) {
				assertEquals(PackExt.INDEX.getPosition()
				assertTrue(cacheHit);
				assertTrue(lastEvictionDuration.isZero());
				loaded.incrementAndGet();
			}
		};

		DfsBlockCache.reconfigure(new DfsBlockCacheConfig().setBlockSize(512)
				.setBlockLimit(512 * 4)
				.setIndexEventConsumer(indexEventConsumer));
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
		assertTrue(cache.getEvictions()[PackExt.PACK.getPosition()] > 0);
		assertEquals(1
		assertEquals(1
	}

	@SuppressWarnings("resource")
	@Test
	public void hasIndexEventConsumerLoadedAndEvicted() throws Exception {
		AtomicInteger loaded = new AtomicInteger();
		AtomicInteger evicted = new AtomicInteger();
		IndexEventConsumer indexEventConsumer = new IndexEventConsumer() {
			@Override
			public void acceptLoadedEvent(int packExtPos
					long loadMicros
					Duration lastEvictionDuration) {
				assertEquals(PackExt.INDEX.getPosition()
				assertTrue(cacheHit);
				assertTrue(lastEvictionDuration.isZero());
				loaded.incrementAndGet();
			}

			@Override
			public void acceptEvictedEvent(int packExtPos
					int totalCacheHitCount
				assertEquals(PackExt.INDEX.getPosition()
				assertTrue(totalCacheHitCount > 0);
				assertTrue(lastEvictionDuration.isZero());
				evicted.incrementAndGet();
			}

			@Override
			public boolean shouldReportEvictedEvent() {
				return true;
			}
		};

		DfsBlockCache.reconfigure(new DfsBlockCacheConfig().setBlockSize(512)
				.setBlockLimit(512 * 4)
				.setIndexEventConsumer(indexEventConsumer));
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
		assertTrue(cache.getEvictions()[PackExt.PACK.getPosition()] > 0);
		assertEquals(1
		assertEquals(1
		assertEquals(1
	}

