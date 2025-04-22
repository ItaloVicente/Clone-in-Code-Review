		testRepo.createInitialCommit("initial commit");
		testRepo.createBranch(MASTER, TEST_1);
		testRepo.createBranch(MASTER, TEST_2);
		testRepo.checkoutBranch(TEST_1);
		File file = testRepo.createFile(iProject, name);
		String path = Repository.stripWorkDir(repo.getWorkTree(), file);
		RevCommit baseCommit = testRepo.addAndCommit(iProject, file,
				"second commit");
		GitBlobResourceVariant base = new GitBlobResourceVariant(repo,
				baseCommit, path);

		testRepo.checkoutBranch(TEST_2);
		File file2 = testRepo.createFile(iProject, name + File.separator
				+ "keep");
		RevCommit remoteCommit = testRepo.addAndCommit(iProject, file2,
				"second commit");
		GitFolderResourceVariant remote = new GitFolderResourceVariant(repo,
				remoteCommit, path);

