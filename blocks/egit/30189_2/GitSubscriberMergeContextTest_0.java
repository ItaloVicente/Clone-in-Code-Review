	@Test
	public void mergeModelWithDeletedFileNoConflict() throws Exception {
		File file1 = testRepo.createFile(iProject, "file1."
				+ SAMPLE_FILE_EXTENSION);
		File file2 = testRepo.createFile(iProject, "file2."
				+ SAMPLE_FILE_EXTENSION);

		String initialContent1 = "some content for the first file";
		String initialContent2 = "some content for the second file";
		testRepo.appendContentAndCommit(iProject, file1, initialContent1,
				"first file - initial commit");
		testRepo.appendContentAndCommit(iProject, file2, initialContent2,
				"second file - initial commit");

		IFile iFile1 = testRepo.getIFile(iProject, file1);
		IFile iFile2 = testRepo.getIFile(iProject, file2);
		String repoRelativePath1 = testRepo.getRepoRelativePath(iFile1
				.getLocation().toPortableString());
		String repoRelativePath2 = testRepo.getRepoRelativePath(iFile2
				.getLocation().toPortableString());

		testRepo.createAndCheckoutBranch(MASTER, BRANCH);

		final String branchChanges = "branch changes\n";
		setContentsAndCommit(repoRelativePath1, iFile1, branchChanges
				+ initialContent1, "branch commit");
		iFile2.delete(true, new NullProgressMonitor());
		testRepo.addAndCommit(iProject, file2, "branch commit - deleted file2."
				+ SAMPLE_FILE_EXTENSION);

		testRepo.checkoutBranch(MASTER);

		final String masterChanges = "some changes\n";
		setContentsAndCommit(repoRelativePath1, iFile1, initialContent1
				+ masterChanges, "master commit");
		iProject.refreshLocal(IResource.DEPTH_INFINITE,
				new NullProgressMonitor());

		IMergeContext mergeContext = prepareModelContext(iFile1, MASTER, BRANCH);
		IDiff node = mergeContext.getDiffTree().getDiff(iFile1);
		assertNotNull(node);
		node = mergeContext.getDiffTree().getDiff(iFile2);
		assertNotNull(node);

		IResourceMappingMerger merger = new SampleModelMerger(
				SAMPLE_PROVIDER_ID);
		IStatus mergeStatus = merger.merge(mergeContext,
				new NullProgressMonitor());
		assertEquals(IStatus.OK, mergeStatus.getSeverity());
		assertContentEquals(iFile1, branchChanges + initialContent1
				+ masterChanges);
		assertFalse(iFile2.exists());

		Status status = new Git(repo).status().call();
		assertEquals(1, status.getChanged().size());
		assertEquals(1, status.getRemoved().size());
		assertEquals(0, status.getModified().size());
		assertTrue(status.getChanged().contains(repoRelativePath1));
		assertTrue(status.getRemoved().contains(repoRelativePath2));
	}

