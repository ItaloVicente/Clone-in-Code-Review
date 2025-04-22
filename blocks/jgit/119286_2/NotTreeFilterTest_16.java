		try (TreeWalk tw = new TreeWalk(db)) {
			final TreeFilter a = TreeFilter.ALL;
			final TreeFilter n = NotTreeFilter.create(a);
			assertNotNull(n);
			assertTrue(a.include(tw));
			assertFalse(n.include(tw));
		}
