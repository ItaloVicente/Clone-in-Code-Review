		InMemoryRepository r2 = new InMemoryRepository(repo);
		content = rng.nextBytes(424242);
		try (ObjectInserter ins = r2.newObjectInserter()) {
			ins.insert(OBJ_BLOB, content);
			ins.flush();
		}
		assertEquals(0, LongStream.of(cache.getMissCount()).sum());
		assertTrue(cache.getEvictions()[PackExt.PACK.getPosition()] > 0);
		assertEquals(0, cache.getEvictions()[PackExt.INDEX.getPosition()]);
