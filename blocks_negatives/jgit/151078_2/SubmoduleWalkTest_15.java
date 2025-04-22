		SubmoduleWalk gen = SubmoduleWalk.forIndex(db);
		gen.setFilter(PathFilter.create(path1));
		assertTrue(gen.next());
		assertEquals(path1, gen.getPath());
		assertEquals(id1, gen.getObjectId());
		assertFalse(gen.next());
