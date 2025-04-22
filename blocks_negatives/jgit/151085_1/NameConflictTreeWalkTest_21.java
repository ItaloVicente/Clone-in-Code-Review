		final NameConflictTreeWalk tw = new NameConflictTreeWalk(db);
		tw.addTree(new DirCacheIterator(tree0));
		tw.addTree(new DirCacheIterator(tree1));

		assertModes("a", REGULAR_FILE, TREE, tw);
		assertTrue(tw.isSubtree());
		assertTrue(tw.isDirectoryFileConflict());
		tw.enterSubtree();
		assertModes("a/b", MISSING, REGULAR_FILE, tw);
		assertTrue(tw.isDirectoryFileConflict());
		assertModes("a.b", MISSING, EXECUTABLE_FILE, tw);
		assertFalse(tw.isDirectoryFileConflict());
		assertModes("a0b", SYMLINK, SYMLINK, tw);
		assertFalse(tw.isDirectoryFileConflict());
