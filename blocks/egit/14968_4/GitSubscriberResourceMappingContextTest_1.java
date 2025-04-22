		testRepo.appendContentAndCommit(iProject, file1,
				"initial content - file 1",
				"first file - initial commit MASTER");
		testRepo.appendContentAndCommit(iProject, file2,
				"initial content - file 2",
				"second file - initial commit MASTER");

		IFile iFile1 = testRepo.getIFile(iProject, file1);
		IFile iFile2 = testRepo.getIFile(iProject, file2);
		String repoRelativePath1 = testRepo.getRepoRelativePath(iFile1
				.getLocation().toPortableString());
		String repoRelativePath2 = testRepo.getRepoRelativePath(iFile2
				.getLocation().toPortableString());

		testRepo.createAndCheckoutBranch(MASTER, BRANCH);

		setContentsAndCommit(repoRelativePath1, iFile1,
				"change in branch - file 1", "branch commit - file1");
		setContentsAndCommit(repoRelativePath2, iFile2,
				"change in branch - file 2", "branch commit - file2");

		testRepo.checkoutBranch(MASTER);

		RemoteResourceMappingContext context = prepareContext(MASTER, BRANCH);
		assertTrue(context.hasRemoteChange(iFile1, new NullProgressMonitor()));
		assertTrue(context.hasRemoteChange(iFile2, new NullProgressMonitor()));
		assertFalse(context.hasLocalChange(iFile2, new NullProgressMonitor()));

		setContentsAndCommit(repoRelativePath1, iFile1,
				"change in master - file 1",
				"first file - second commit MASTER");
		refresh(context, iFile1);
		assertTrue(context.hasRemoteChange(iFile1, new NullProgressMonitor()));
		assertTrue(context.hasRemoteChange(iFile2, new NullProgressMonitor()));
		assertFalse(context.hasLocalChange(iFile2, new NullProgressMonitor()));

		setContents(iFile2, "change in branch - file 2");
		refresh(context, iFile2);
		assertTrue(context.hasRemoteChange(iFile1, new NullProgressMonitor()));
		assertFalse(context.hasRemoteChange(iFile2, new NullProgressMonitor()));
		assertFalse(context.hasLocalChange(iFile2, new NullProgressMonitor()));

		setContentsAndCommit(repoRelativePath2, iFile2,
				"change in branch - file 2",
				"change in master (same as in branch) - file 2");
		refresh(context, iFile2);
		assertTrue(context.hasRemoteChange(iFile1, new NullProgressMonitor()));
		assertFalse(context.hasRemoteChange(iFile2, new NullProgressMonitor()));
		assertFalse(context.hasLocalChange(iFile2, new NullProgressMonitor()));
	}

	@Test
	public void hasRemoteChangeInNewFile() throws Exception {
		File file1 = testRepo.createFile(iProject, "file1.sample");
