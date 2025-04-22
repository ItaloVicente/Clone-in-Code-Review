	@Test
	public void testDF_specialFileNames() throws Exception {
		final DirCache tree0 = db.readDirCache();
		final DirCache tree1 = db.readDirCache();
		final DirCache tree2 = db.readDirCache();
		{
			final DirCacheBuilder b0 = tree0.builder();
			final DirCacheBuilder b1 = tree1.builder();
			final DirCacheBuilder b2 = tree2.builder();

			b0.add(createEntry("gradle.properties"
			b0.add(createEntry("gradle/nested_file.txt"

			b1.add(createEntry("gradle.properties"

			b2.add(createEntry("gradle"
			b2.add(createEntry("gradle.properties"

			b0.finish();
			b1.finish();
			b2.finish();
			assertEquals(2
			assertEquals(1
			assertEquals(2
		}

		try (NameConflictTreeWalk tw = new NameConflictTreeWalk(db)) {
			tw.addTree(new DirCacheIterator(tree0));
			tw.addTree(new DirCacheIterator(tree1));
			tw.addTree(new DirCacheIterator(tree2));

			assertModes("gradle"
			assertTrue(tw.isSubtree());
			assertTrue(tw.isDirectoryFileConflict());
			tw.enterSubtree();
			assertModes("gradle/nested_file.txt"
			assertFalse(tw.isSubtree());
			assertTrue(tw.isDirectoryFileConflict());
			assertModes("gradle.properties"
			assertFalse(tw.isSubtree());
			assertFalse(tw.isDirectoryFileConflict());
		}
	}

