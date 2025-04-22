	@Test
	public void testEmptyIteratorOnEmptyDirectory() throws Exception {
		String nonExistingFileName = "not-existing-file";
		final File r = new File(trash
		assertFalse(r.exists());
		FileUtils.mkdir(r);

		final FileTreeIterator parent = new FileTreeIterator(db);

		while (!parent.getEntryPathString().equals(nonExistingFileName))
			parent.next(1);

		final FileTreeIterator childIter = new FileTreeIterator(parent
				db.getFS());
		assertTrue(childIter.first());
		assertTrue(childIter.eof());

		String parentPath = parent.getEntryPathString();
		assertEquals(nonExistingFileName

		String childPath = childIter.getEntryPathString();

		EmptyTreeIterator e = childIter.createEmptyTreeIterator();
		assertNotNull(e);

		assertEquals(parentPath
		assertEquals(parentPath + "/"
		assertEquals(parentPath + "/"
		assertEquals(childPath + "/"
	}

