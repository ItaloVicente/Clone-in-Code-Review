		try (TreeWalk tw = new TreeWalk(db)) {
			tw.setRecursive(true);
			tw.addTree(new FileTreeIterator(db));
			assertTrue(tw.next());
			assertEquals("link"
			assertTrue(tw.next());
			assertEquals("target/data"
			assertFalse(tw.next());
		}
