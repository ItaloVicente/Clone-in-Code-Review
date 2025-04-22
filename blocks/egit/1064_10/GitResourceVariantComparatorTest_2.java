		RevCommit baseCommit = testRepo.createInitialCommit("initial commit");
		testRepo.createAndCheckoutBranch(Constants.HEAD, Constants.R_HEADS
				+ "test");
		File file = testRepo.createFile(iProject, "test-file");
		RevCommit remoteCommit = testRepo.addAndCommit(iProject, file,
				"second commit");
		String path = Repository.stripWorkDir(repo.getWorkTree(), file);

		GitBlobResourceVariant base = new GitBlobResourceVariant(repo,
				baseCommit, path);
		GitBlobResourceVariant remote = new GitBlobResourceVariant(repo,
				remoteCommit, path);
