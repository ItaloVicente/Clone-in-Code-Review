		TreeWalk tw = new TreeWalk(db);
		tw.setRecursive(true);
		tw.addTree(new FileTreeIterator(db));
		assertTrue(tw.next());
		assertEquals("link", tw.getPathString());
		assertTrue(tw.next());
		assertEquals("target/data", tw.getPathString());
		assertFalse(tw.next());
