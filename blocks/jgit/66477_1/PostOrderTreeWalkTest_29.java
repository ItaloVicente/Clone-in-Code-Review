		final DirCacheBuilder b = tree.builder();

		b.add(makeFile("a"));
		b.add(makeFile("b/c"));
		b.add(makeFile("b/d"));
		b.add(makeFile("q"));

		b.finish();
		assertEquals(4

		try (final TreeWalk tw = new TreeWalk(db)) {
			tw.setPostOrderTraversal(false);
			tw.addTree(new DirCacheIterator(tree));

			assertModes("a"
			assertModes("b"
			assertTrue(tw.isSubtree());
			assertFalse(tw.isPostChildren());
			tw.enterSubtree();
			assertModes("b/c"
			assertModes("b/d"
			assertModes("q"
