		try (NameConflictTreeWalk tw = new NameConflictTreeWalk(db)) {
			tw.addTree(new DirCacheIterator(tree0));
			tw.addTree(new DirCacheIterator(tree1));

			assertModes("0"
			assertFalse(tw.isDirectoryFileConflict());
			assertModes("a"
			assertTrue(tw.isSubtree());
			assertTrue(tw.isDirectoryFileConflict());
			tw.enterSubtree();
			assertModes("a/b"
			assertTrue(tw.isDirectoryFileConflict());
			assertModes("a/c"
			assertTrue(tw.isDirectoryFileConflict());
			tw.enterSubtree();
			assertModes("a/c/e"
			assertTrue(tw.isDirectoryFileConflict());

			assertModes("a.b"
			assertFalse(tw.isDirectoryFileConflict());
		}
