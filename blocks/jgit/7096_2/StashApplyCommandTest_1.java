	@Test
	public void stashChangeInANewSubdirectory() throws Exception {
		String subdir = "subdir";
		String fname = "file2.txt";
		String path = subdir + System.getProperty("file.separator") + fname;
		String otherBranch = "otherbranch";

		writeTrashFile(subdir

		git.add().addFilepattern(path).call();
		RevCommit stashed = git.stashCreate().call();
		assertNotNull(stashed);
		assertTrue(git.status().call().isClean());

		git.branchCreate().setName(otherBranch).call();
		git.checkout().setName(otherBranch).call();

		ObjectId unstashed = git.stashApply().call();
		assertEquals(stashed

		Status status = git.status().call();
		assertTrue(status.getChanged().isEmpty());
		assertTrue(status.getConflicting().isEmpty());
		assertTrue(status.getMissing().isEmpty());
		assertTrue(status.getRemoved().isEmpty());
		assertTrue(status.getModified().isEmpty());
		assertTrue(status.getUntracked().isEmpty());

		assertEquals(1
		assertTrue(status.getAdded().contains(path));
	}

