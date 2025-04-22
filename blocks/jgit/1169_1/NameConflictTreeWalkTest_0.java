		assertFalse(tw.isDirectoryFileConflict());
	}

	public void testDF_DetectConflict() throws Exception {
		final DirCache tree0 = DirCache.read(db);
		final DirCache tree1 = DirCache.read(db);
		{
			final DirCacheBuilder b0 = tree0.builder();
			final DirCacheBuilder b1 = tree1.builder();

			b0.add(makeEntry("0"
			b0.add(makeEntry("a"
			b1.add(makeEntry("0"
			b1.add(makeEntry("a.b"
			b1.add(makeEntry("a/b"
			b1.add(makeEntry("a/c/e"

			b0.finish();
			b1.finish();
			assertEquals(2
			assertEquals(4
		}

		final NameConflictTreeWalk tw = new NameConflictTreeWalk(db);
		tw.reset();
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
