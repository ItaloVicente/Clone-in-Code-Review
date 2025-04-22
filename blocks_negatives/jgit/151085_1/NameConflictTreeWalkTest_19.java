		final NameConflictTreeWalk tw = new NameConflictTreeWalk(db);
		tw.addTree(new DirCacheIterator(tree0));
		tw.addTree(new DirCacheIterator(tree1));

		assertModes("a", REGULAR_FILE, TREE, tw);
		assertTrue(tw.isDirectoryFileConflict());
		assertTrue(tw.isSubtree());
		tw.enterSubtree();
		assertModes("a/b", MISSING, REGULAR_FILE, tw);
		assertTrue(tw.isDirectoryFileConflict());
		assertModes("a.b", EXECUTABLE_FILE, MISSING, tw);
		assertFalse(tw.isDirectoryFileConflict());
		assertModes("a0b", SYMLINK, MISSING, tw);
		assertFalse(tw.isDirectoryFileConflict());
