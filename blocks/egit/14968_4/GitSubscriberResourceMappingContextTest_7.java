	@Test
	public void hasLocalAndRemoteChangeInSubFolder() throws Exception {
		File file1 = testRepo.createFile(iProject, "folder/file1.sample");
		testRepo.appendContentAndCommit(iProject, file1, "initial content",
				"first commit in master");
		IFile iFile1 = testRepo.getIFile(iProject, file1);
		String repoRelativePath1 = testRepo.getRepoRelativePath(iFile1
				.getLocation().toPortableString());

		testRepo.createAndCheckoutBranch(MASTER, BRANCH);
		setContentsAndCommit(repoRelativePath1, iFile1,
				"changed content in branch", "first commit in BRANCH");

		testRepo.checkoutBranch(MASTER);
		setContentsAndCommit(repoRelativePath1, iFile1,
				"changed content in master", "second commit in MASTER");

		RemoteResourceMappingContext context = prepareContext(MASTER, BRANCH);
