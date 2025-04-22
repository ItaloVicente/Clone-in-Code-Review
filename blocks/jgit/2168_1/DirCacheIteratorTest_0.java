
	public void testRemovedSubtree() throws Exception {
		final File path = JGitTestUtil
				.getTestResourceFile("dircache.testRemovedSubtree");

		final DirCache dc = DirCache.read(path
		assertEquals(2

		final TreeWalk tw = new TreeWalk(db);
		tw.setRecursive(true);
		tw.addTree(new DirCacheIterator(dc));

		assertTrue(tw.next());
		assertEquals("a/a"
		assertSame(FileMode.REGULAR_FILE

		assertTrue(tw.next());
		assertEquals("q"
		assertSame(FileMode.REGULAR_FILE

		assertFalse(tw.next());
	}
