		final TreeWalk tw = new TreeWalk(db);
		tw.addTree(new DirCacheIterator(tree0));
		tw.addTree(new DirCacheIterator(tree1));

		assertModes("a", REGULAR_FILE, MISSING, tw);
		assertModes("a.b", EXECUTABLE_FILE, MISSING, tw);
		assertModes("a", MISSING, TREE, tw);
		tw.enterSubtree();
		assertModes("a/b", MISSING, REGULAR_FILE, tw);
		assertModes("a0b", SYMLINK, MISSING, tw);
