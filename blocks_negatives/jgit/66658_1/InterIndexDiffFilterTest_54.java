		TreeWalk tw = new TreeWalk(db);
		tw.setRecursive(true);
		tw.addTree(new DirCacheIterator(dc1));
		tw.addTree(new DirCacheIterator(dc2));
		tw.setFilter(InterIndexDiffFilter.INSTANCE);
		assertTrue(tw.next());
		assertEquals("a/a", tw.getPathString());
		assertFalse(tw.next());
