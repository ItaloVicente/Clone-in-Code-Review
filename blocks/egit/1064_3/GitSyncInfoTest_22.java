		testRepo.createInitialCommit("initial commit");
		testRepo.createBranch(MASTER, TEST_1);
		testRepo.createBranch(MASTER, TEST_2);
		testRepo.checkoutBranch(TEST_1);
		File file = testRepo.createFile(iProject, name + File.separator
				+ "keep");
		RevCommit baseCommit = testRepo.addAndCommit(iProject, file,
				"second commit");

		String path = Repository.stripWorkDir(repo.getWorkDir(),
				new File(file.getParent()));
		GitFolderResourceVariant base = new GitFolderResourceVariant(repo,
				baseCommit, path);

		testRepo.checkoutBranch(TEST_2);
		File file2 = testRepo.createFile(iProject, name);
		RevCommit remoteCommit = testRepo.addAndCommit(iProject, file2,
				"second commit");
		GitBlobResourceVariant remote = new GitBlobResourceVariant(repo,
				remoteCommit, path);

