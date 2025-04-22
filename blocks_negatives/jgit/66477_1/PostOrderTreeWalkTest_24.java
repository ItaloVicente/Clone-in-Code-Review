
		final TreeWalk tw = new TreeWalk(db);
		tw.setPostOrderTraversal(false);
		tw.addTree(new DirCacheIterator(tree));

		assertModes("a", REGULAR_FILE, tw);
		assertModes("b", TREE, tw);
		assertTrue(tw.isSubtree());
		assertFalse(tw.isPostChildren());
		tw.enterSubtree();
		assertModes("b/c", REGULAR_FILE, tw);
		assertModes("b/d", REGULAR_FILE, tw);
		assertModes("q", REGULAR_FILE, tw);
