		try (NameConflictTreeWalk tw = new NameConflictTreeWalk(db)) {
			tw.addTree(new DirCacheIterator(tree0));
			tw.addTree(new DirCacheIterator(tree1));

			assertModes("a"
			assertTrue(tw.isSubtree());
			assertTrue(tw.isDirectoryFileConflict());
			tw.enterSubtree();
			assertModes("a/b"
			assertTrue(tw.isDirectoryFileConflict());
			assertModes("a.b"
			assertFalse(tw.isDirectoryFileConflict());
			assertModes("a0b"
			assertFalse(tw.isDirectoryFileConflict());
		}
