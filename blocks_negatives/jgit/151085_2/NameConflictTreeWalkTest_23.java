		final NameConflictTreeWalk tw = new NameConflictTreeWalk(db);
		tw.addTree(new DirCacheIterator(tree0));
		tw.addTree(new DirCacheIterator(tree1));

		assertModes("0", REGULAR_FILE, REGULAR_FILE, tw);
		assertFalse(tw.isDirectoryFileConflict());
		assertModes("a", REGULAR_FILE, TREE, tw);
		assertTrue(tw.isSubtree());
		assertTrue(tw.isDirectoryFileConflict());
		tw.enterSubtree();
		assertModes("a/b", MISSING, REGULAR_FILE, tw);
		assertTrue(tw.isDirectoryFileConflict());
		assertModes("a/c", MISSING, TREE, tw);
		assertTrue(tw.isDirectoryFileConflict());
		tw.enterSubtree();
		assertModes("a/c/e", MISSING, REGULAR_FILE, tw);
		assertTrue(tw.isDirectoryFileConflict());

		assertModes("a.b", MISSING, REGULAR_FILE, tw);
		assertFalse(tw.isDirectoryFileConflict());
