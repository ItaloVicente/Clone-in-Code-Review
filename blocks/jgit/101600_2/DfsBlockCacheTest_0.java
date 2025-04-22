
	@Test
	public void weirdBlockSize() throws Exception {
		DfsRepositoryDescription repo = new DfsRepositoryDescription("test");
		InMemoryRepository r1 = new InMemoryRepository(repo);

		byte[] content1 = rng.nextBytes(4);
		byte[] content2 = rng.nextBytes(424242);
		ObjectId id1;
		ObjectId id2;
		try (ObjectInserter ins = r1.newObjectInserter()) {
			id1 = ins.insert(OBJ_BLOB
			id2 = ins.insert(OBJ_BLOB
			ins.flush();
		}

		resetCache();
		List<DfsPackDescription> packs = r1.getObjectDatabase().listPacks();

		InMemoryRepository r2 = new InMemoryRepository(repo);
		r2.getObjectDatabase().setReadableChannelBlockSizeForTest(500);
		r2.getObjectDatabase().commitPack(packs
		try (ObjectReader rdr = r2.newObjectReader()) {
			byte[] actual = rdr.open(id1
			assertTrue(Arrays.equals(content1
		}

		InMemoryRepository r3 = new InMemoryRepository(repo);
		r3.getObjectDatabase().setReadableChannelBlockSizeForTest(500);
		r3.getObjectDatabase().commitPack(packs
		try (ObjectReader rdr = r3.newObjectReader()) {
			byte[] actual = rdr.open(id2
			assertTrue(Arrays.equals(content2
		}
	}

	private void resetCache() {
		DfsBlockCache.reconfigure(new DfsBlockCacheConfig()
				.setBlockSize(512)
				.setBlockLimit(1 << 20));
		cache = DfsBlockCache.getInstance();
	}
