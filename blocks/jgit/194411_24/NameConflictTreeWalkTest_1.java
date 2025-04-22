	@Test
	public void tesdDF_LastItemsInTreeHasDFConflictAndSpecialNames()
			throws Exception {

		final DirCache tree0 = db.readDirCache();
		final DirCache tree1 = db.readDirCache();

		final DirCacheBuilder b0 = tree0.builder();
		final DirCacheBuilder b1 = tree1.builder();
		b0.add(createEntry("subtree"
		b0.add(createEntry("subtree-0"
		b1.add(createEntry("subtree/file"
		b1.add(createEntry("subtree-0"

		b0.finish();
		b1.finish();

		try (NameConflictTreeWalk tw = new NameConflictTreeWalk(db)) {
			tw.addTree(new DirCacheIterator(tree0));
			tw.addTree(new DirCacheIterator(tree1));

			assertModes("subtree"
			assertTrue(tw.isSubtree());
			assertTrue(tw.isDirectoryFileConflict());
			tw.enterSubtree();
			assertModes("subtree/file"
			assertFalse(tw.isSubtree());
			assertTrue(tw.isDirectoryFileConflict());
			assertModes("subtree-0"
			assertFalse(tw.isSubtree());
			assertFalse(tw.isDirectoryFileConflict());
			assertFalse(tw.next());
		}
	}

	@Test
	public void tesdDF_LastItemsInTreeHasDFConflictAndSpecialNames2()
			throws Exception {

		final DirCache tree0 = db.readDirCache();
		final DirCache tree1 = db.readDirCache();

		final DirCacheBuilder b0 = tree0.builder();
		final DirCacheBuilder b1 = tree1.builder();
		b0.add(createEntry("subtree/file"
		b0.add(createEntry("subtree-0"
		b1.add(createEntry("subtree"
		b1.add(createEntry("subtree-0"

		b0.finish();
		b1.finish();

		try (NameConflictTreeWalk tw = new NameConflictTreeWalk(db)) {
			tw.addTree(new DirCacheIterator(tree0));
			tw.addTree(new DirCacheIterator(tree1));

			assertModes("subtree"
			assertTrue(tw.isSubtree());
			assertTrue(tw.isDirectoryFileConflict());
			tw.enterSubtree();
			assertModes("subtree/file"
			assertFalse(tw.isSubtree());
			assertTrue(tw.isDirectoryFileConflict());
			assertModes("subtree-0"
			assertFalse(tw.isSubtree());
			assertFalse(tw.isDirectoryFileConflict());
			assertFalse(tw.next());
		}
	}

	@Test
	public void tesdDF_NonLastItemsInTreeHasDFConflictAndSpecialNames()
			throws Exception {
		final DirCache tree0 = db.readDirCache();
		final DirCache tree1 = db.readDirCache();

		final DirCacheBuilder b0 = tree0.builder();
		final DirCacheBuilder b1 = tree1.builder();
		b0.add(createEntry("subtree"
		b0.add(createEntry("subtree-0"
		b0.add(createEntry("x"

		b1.add(createEntry("subtree/file"
		b1.add(createEntry("subtree-0"
		b1.add(createEntry("x"

		b0.finish();
		b1.finish();

		try (NameConflictTreeWalk tw = new NameConflictTreeWalk(db)) {
			tw.addTree(new DirCacheIterator(tree0));
			tw.addTree(new DirCacheIterator(tree1));

			assertModes("subtree"
			assertTrue(tw.isSubtree());
			assertTrue(tw.isDirectoryFileConflict());
			tw.enterSubtree();
			assertModes("subtree/file"
			assertFalse(tw.isSubtree());
			assertTrue(tw.isDirectoryFileConflict());
			assertModes("subtree-0"
			assertFalse(tw.isSubtree());
			assertFalse(tw.isDirectoryFileConflict());
			assertModes("x"
			assertFalse(tw.isSubtree());
			assertFalse(tw.isDirectoryFileConflict());
			assertFalse(tw.next());
		}
	}

	@Test
	public void tesdDF_NoSpecialNames() throws Exception {
		final DirCache tree0 = db.readDirCache();
		final DirCache tree1 = db.readDirCache();

		final DirCacheBuilder b0 = tree0.builder();
		final DirCacheBuilder b1 = tree1.builder();
		b0.add(createEntry("subtree"
		b0.add(createEntry("xubtree-0"

		b1.add(createEntry("subtree/file"
		b1.add(createEntry("xubtree-0"

		b0.finish();
		b1.finish();

		try (NameConflictTreeWalk tw = new NameConflictTreeWalk(db)) {
			tw.addTree(new DirCacheIterator(tree0));
			tw.addTree(new DirCacheIterator(tree1));

			assertModes("subtree"
			assertTrue(tw.isSubtree());
			assertTrue(tw.isDirectoryFileConflict());
			tw.enterSubtree();
			assertModes("subtree/file"
			assertFalse(tw.isSubtree());
			assertTrue(tw.isDirectoryFileConflict());
			assertModes("xubtree-0"
			assertFalse(tw.isSubtree());
			assertFalse(tw.isDirectoryFileConflict());
			assertFalse(tw.next());
		}
	}

