
		testRepo.checkoutBranch(MASTER);

		RemoteResourceMappingContext context = prepareContext(MASTER, BRANCH);
		assertFalse(context.hasRemoteChange(iFile1, new NullProgressMonitor()));
		assertTrue(context.hasRemoteChange(iFile2, new NullProgressMonitor()));
	}

	@Test
	public void hasLocalAndRemoteChange() throws Exception {
		File file1 = testRepo.createFile(iProject, "file1.sample");
		testRepo.appendContentAndCommit(iProject, file1, "initial content",
				"first commit in master");
		IFile iFile1 = testRepo.getIFile(iProject, file1);
