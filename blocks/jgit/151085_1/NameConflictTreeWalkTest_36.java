		try (TreeWalk tw = new TreeWalk(db)) {
			tw.addTree(new DirCacheIterator(tree0));
			tw.addTree(new DirCacheIterator(tree1));

			assertModes("a"
			assertModes("a.b"
			assertModes("a"
			tw.enterSubtree();
			assertModes("a/b"
			assertModes("a0b"
		}
