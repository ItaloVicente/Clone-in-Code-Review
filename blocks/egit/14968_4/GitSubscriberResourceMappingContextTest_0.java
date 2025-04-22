	@Test
	public void hasLocalChange() throws Exception {
		File file1 = testRepo.createFile(iProject, "a.txt");
		File file2 = testRepo.createFile(iProject, "b.txt");
		testRepo.appendContentAndCommit(iProject, file1, "content a",
				"commit a");
		testRepo.appendContentAndCommit(iProject, file2, "content b",
				"commit b");
		JGitTestUtil.write(file1, "changed content a");
		JGitTestUtil.write(file2, "changed content b");
		IFile iFile1 = testRepo.getIFile(iProject, file1);
		IFile iFile2 = testRepo.getIFile(iProject, file2);
		RemoteResourceMappingContext context = prepareContext(MASTER, MASTER);
		assertTrue(context.hasLocalChange(iFile1, new NullProgressMonitor()));
		assertTrue(context.hasLocalChange(iFile2, new NullProgressMonitor()));

		JGitTestUtil.write(file2, "content b");
		refresh(context, iFile2);
		assertTrue(context.hasLocalChange(iFile1, new NullProgressMonitor()));
		assertFalse(context.hasLocalChange(iFile2, new NullProgressMonitor()));
	}

	@Test
	public void hasLocalChangeInNewFolder() throws Exception {
		File file2 = testRepo.createFile(iProject, "folder/b.txt");
		RemoteResourceMappingContext context = prepareContext(MASTER, MASTER);
		IFile iFile2 = testRepo.getIFile(iProject, file2);
		assertTrue(context.hasLocalChange(iFile2, new NullProgressMonitor()));

		testRepo.addToIndex(iProject, file2);
		refresh(context, iFile2);
		assertTrue(context.hasLocalChange(iFile2, new NullProgressMonitor()));

		JGitTestUtil.write(file2, "changed content b");
		refresh(context, iFile2);
		assertTrue(context.hasLocalChange(iFile2, new NullProgressMonitor()));
	}

