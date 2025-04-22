	@Test
	public void testFileModeChangeWithNoContentChangeUpdate() throws Exception {
		if (!FS.DETECTED.supportsExecute())
			return;

		Git git = Git.wrap(db);

		File file = writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("commit1").call();
		assertFalse(db.getFS().canExecute(file));

		git.branchCreate().setName("b1").call();

		db.getFS().setExecute(file
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("commit2").call();

		Status status = git.status().call();
		assertTrue(status.getModified().isEmpty());
		assertTrue(status.getChanged().isEmpty());
		assertTrue(db.getFS().canExecute(file));

		git.checkout().setName("b1").call();

		status = git.status().call();
		assertTrue(status.getModified().isEmpty());
		assertTrue(status.getChanged().isEmpty());
		assertFalse(db.getFS().canExecute(file));
	}

	@Test
	public void testFileModeChangeAndContentChangeConflict() throws Exception {
		if (!FS.DETECTED.supportsExecute())
			return;

		Git git = Git.wrap(db);

		File file = writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("commit1").call();
		assertFalse(db.getFS().canExecute(file));

		git.branchCreate().setName("b1").call();

		db.getFS().setExecute(file
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("commit2").call();

		Status status = git.status().call();
		assertTrue(status.getModified().isEmpty());
		assertTrue(status.getChanged().isEmpty());
		assertTrue(db.getFS().canExecute(file));

		writeTrashFile("file.txt"

		CheckoutCommand checkout = git.checkout().setName("b1");
		try {
			checkout.call();
			fail("Checkout exception not thrown");
		} catch (JGitInternalException e) {
			CheckoutResult result = checkout.getResult();
			assertNotNull(result);
			assertNotNull(result.getConflictList());
			assertEquals(1
			assertTrue(result.getConflictList().contains("file.txt"));
		}
	}

