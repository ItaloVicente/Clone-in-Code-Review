		testRepo.createInitialCommit("initial commit");
		File file = testRepo.createFile(iProject, fileName);
		testRepo.appendContentAndCommit(iProject, file, localBytes,
				"initial commit");
		testRepo.createBranch(MASTER, TEST_1);
		testRepo.createBranch(MASTER, TEST_2);

		testRepo.checkoutBranch(TEST_1);
		RevCommit baseCommit = testRepo.appendContentAndCommit(iProject, file,
				"a", "first commit");
		String repoRelPath = Repository.stripWorkDir(repo.getWorkTree(), file);
		GitBlobResourceVariant base = new GitBlobResourceVariant(repo,
				baseCommit, repoRelPath);

		testRepo.checkoutBranch(TEST_2);
		RevCommit remoteCommit = testRepo.appendContentAndCommit(iProject,
				file, "bc", "first commit");
		GitBlobResourceVariant remote = new GitBlobResourceVariant(repo,
				remoteCommit, repoRelPath);
