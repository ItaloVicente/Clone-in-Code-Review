
	@Test
	public void untrackedFileNotIncluded() throws Exception {
		String untrackedPath = "untracked.txt";
		File untrackedFile = writeTrashFile(untrackedPath
		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		git.stashCreate().call();
		assertTrue(untrackedFile.exists());

		git.stashApply().setStashRef("stash@{0}").call();
		assertTrue(untrackedFile.exists());

		Status status = git.status().call();
		assertEquals(1
		assertTrue(status.getUntracked().contains(untrackedPath));
		assertEquals(1
		assertTrue(status.getChanged().contains(PATH));
		assertTrue(status.getAdded().isEmpty());
		assertTrue(status.getConflicting().isEmpty());
		assertTrue(status.getMissing().isEmpty());
		assertTrue(status.getRemoved().isEmpty());
		assertTrue(status.getModified().isEmpty());
	}

	@Test
	public void untrackedFileIncluded() throws Exception {
		String path = "a/b/untracked.txt";
		File untrackedFile = writeTrashFile(path
		RevCommit stashedCommit = git.stashCreate().setIncludeUntracked(true)
				.call();
		assertNotNull(stashedCommit);
		assertFalse(untrackedFile.exists());

		git.stashApply().setStashRef("stash@{0}").call();
		assertTrue(untrackedFile.exists());
		assertEquals("content"

		Status status = git.status().call();
		assertEquals(1
		assertTrue(status.getAdded().isEmpty());
		assertTrue(status.getChanged().isEmpty());
		assertTrue(status.getConflicting().isEmpty());
		assertTrue(status.getMissing().isEmpty());
		assertTrue(status.getRemoved().isEmpty());
		assertTrue(status.getModified().isEmpty());
		assertTrue(status.getUntracked().contains(path));
	}

	@Test
	public void untrackedFileConflictsWithCommit() throws Exception {
		String path = "untracked.txt";
		writeTrashFile(path
		git.stashCreate().setIncludeUntracked(true).call();

		writeTrashFile(path
		head = git.commit().setMessage("add file").call();
		git.add().addFilepattern(path).call();
		git.commit().setMessage("conflicting commit").call();

		try {
			git.stashApply().setStashRef("stash@{0}").call();
			fail("StashApplyFailureException should be thrown.");
		} catch (StashApplyFailureException e) {
			assertEquals(e.getMessage()
		}
		assertEquals("committed"
	}

	@Test
	public void untrackedFileConflictsWithWorkingDirectory()
			throws Exception {
		String path = "untracked.txt";
		writeTrashFile(path
		git.stashCreate().setIncludeUntracked(true).call();

		writeTrashFile(path
		try {
			git.stashApply().setStashRef("stash@{0}").call();
			fail("StashApplyFailureException should be thrown.");
		} catch (StashApplyFailureException e) {
			assertEquals(e.getMessage()
		}
		assertEquals("working-directory"
	}
