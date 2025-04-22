		RevCommit baseCommit = testRepo.createInitialCommit("initial commit");

		testRepo.createAndCheckoutBranch(MASTER, TEST_1);
		File keep = testRepo.createFile(iProject, name + File.separator
				+ "keep");
		RevCommit revCommit = testRepo.addAndCommit(iProject, keep,
				"second commit");

		File parent = new File(keep.getParent());
		String path = Repository.stripWorkDir(repo.getWorkDir(), parent);
		GitFolderResourceVariant base = new GitFolderResourceVariant(repo,
				baseCommit, path);

		GitFolderResourceVariant remote = new GitFolderResourceVariant(repo,
				revCommit, path);

