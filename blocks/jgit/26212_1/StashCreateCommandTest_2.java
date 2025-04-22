
	@Test
	public void untrackedFileIncluded() throws Exception {
		String trackedPath = "tracked.txt";
		writeTrashFile(trackedPath
		git.add().addFilepattern(trackedPath).call();

		RevCommit stashed = git.stashCreate()
				.setIncludeUntracked(true).call();
		assertNotNull(stashed);
		validateStashedCommitWithAdditionalParent(stashed);

		assertEquals(
				"Expected commits for workingDir
				3
		assertFalse("untracked file should be deleted."
	}

	@Test
	public void untrackedFileNotIncluded() throws Exception {
		String trackedPath = "tracked.txt";
		writeTrashFile(trackedPath
		git.add().addFilepattern(trackedPath).call();

		RevCommit stashed = git.stashCreate().call();
		assertNotNull(stashed);
		validateStashedCommit(stashed);

		assertTrue("untracked file should be left untouched."
				untrackedFile.exists());
		assertEquals("content"
	}
