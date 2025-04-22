	@Test
	public void testSkipConflicts() throws Exception {
		RevCommit headCommit = commitFile("a"
		RevCommit checkoutCommit = commitFile("a"
		String workDirContent = "changed in work dir";
		writeTrashFile("a"

		dco = createDirCacheCheckout(headCommit
		dco.setFailOnConflict(false);
		dco.setSkipConflicts(true);
		boolean checkoutOk = dco.checkout();

		assertTrue(checkoutOk);
		assertArrayEquals(new String[] { "a" }
		assertEquals(workDirContent
	}

	@Test
	public void testSkipConflictsWithFileFolderConflict() throws Exception {
		RevCommit headCommit = commitFile("a"
		RevCommit checkoutCommit = commitFile("a"
		deleteTrashFile("a");
		File folder = new File(trash
		folder.mkdir();

		dco = createDirCacheCheckout(headCommit
		dco.setFailOnConflict(false);
		dco.setSkipConflicts(true);
		boolean checkoutOk = dco.checkout();

		assertTrue(checkoutOk);
		assertArrayEquals(new String[] { "a" }
		assertTrue(folder.isDirectory());
	}

	@Test
	public void testSkipConflictsWithFolderFileConflict() throws Exception {
		RevCommit headCommit = commitFile("f/a"
		RevCommit checkoutCommit = commitFile("f/a"
		FileUtils.delete(new File(db.getWorkTree()
		writeTrashFile("f"

		dco = createDirCacheCheckout(headCommit
		dco.setFailOnConflict(false);
		dco.setSkipConflicts(true);
		boolean checkoutOk = dco.checkout();

		assertTrue(checkoutOk);
		assertArrayEquals(new String[] { "f" }
		assertEquals("file instead of folder"
	}

	@Test
	public void testFailOnConflictOverridesSkipConflicts() throws Exception {
		RevCommit headCommit = commitFile("a"
		RevCommit checkoutCommit = commitFile("a"
		String workDirContent = "changed in work dir";
		writeTrashFile("a"
		dco = createDirCacheCheckout(headCommit
		dco.setFailOnConflict(true);
		dco.setSkipConflicts(true);

		try {
			dco.checkout();
			fail();
		} catch (CheckoutConflictException expected) {
			assertArrayEquals(new String[] { "a" }
					expected.getConflictingFiles());
		}
	}

	private DirCacheCheckout createDirCacheCheckout(RevCommit headCommit
			RevCommit checkoutCommit) throws IOException {
		return new DirCacheCheckout(db
				checkoutCommit.getTree());
	}

