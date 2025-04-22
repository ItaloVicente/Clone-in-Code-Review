	@Test(expected = CheckoutConflictException.class)
	public void testSkipConflictsWithFolderFileConflict() throws Exception {
		RevCommit headCommit = commitFile("f/a"
		RevCommit checkoutCommit = commitFile("f/a"
		FileUtils.delete(new File(db.getWorkTree()
		writeTrashFile("f"
		new DirCacheCheckout(db
				checkoutCommit.getTree()).checkout();
	}

	@Test
	public void testMultipleContentConflicts() throws Exception {
		commitFile("a"
		RevCommit headCommit = commitFile("b"
		commitFile("a"
		RevCommit checkoutCommit = commitFile("b"
		writeTrashFile("a"
		writeTrashFile("b"

		try {
			new DirCacheCheckout(db
					checkoutCommit.getTree()).checkout();
			fail();
		} catch (CheckoutConflictException expected) {
			assertEquals(2
			assertTrue(Arrays.asList(expected.getConflictingFiles())
					.contains("a"));
			assertTrue(Arrays.asList(expected.getConflictingFiles())
					.contains("b"));
			assertEquals("changed content"
			assertEquals("changed content"
		}
	}

	@Test
	public void testFolderFileAndContentConflicts() throws Exception {
		RevCommit headCommit = commitFile("f/a"
		commitFile("b"
		RevCommit checkoutCommit = commitFile("f/a"
		FileUtils.delete(new File(db.getWorkTree()
		writeTrashFile("f"
		writeTrashFile("b"

		try {
			new DirCacheCheckout(db
					checkoutCommit.getTree()).checkout();
			fail();
		} catch (CheckoutConflictException expected) {
			assertEquals(2
			assertTrue(Arrays.asList(expected.getConflictingFiles())
					.contains("b"));
			assertTrue(Arrays.asList(expected.getConflictingFiles())
					.contains("f"));
			assertEquals("file instead of folder"
			assertEquals("changed content"
		}
	}

