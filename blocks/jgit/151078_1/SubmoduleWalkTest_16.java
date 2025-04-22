		try (SubmoduleWalk gen = SubmoduleWalk.forIndex(db)) {
			gen.setFilter(PathFilter.create(path1));
			assertTrue(gen.next());
			assertEquals(path1
			assertEquals(id1
			assertFalse(gen.next());
		}
