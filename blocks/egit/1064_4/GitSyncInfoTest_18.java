		testRepo.createInitialCommit("initial commit");
		testRepo.createBranch(MASTER, TEST_1);
		testRepo.createBranch(MASTER, TEST_2);
		testRepo.checkoutBranch(TEST_1);
		File file = testRepo.createFile(iProject, name);
		RevCommit fileCommit = testRepo.addAndCommit(iProject, file,
				"initial commit");
		String path = Repository.stripWorkDir(repo.getWorkDir(), file);
		GitBlobResourceVariant base = new GitBlobResourceVariant(repo,
				fileCommit, path);

		testRepo.checkoutBranch(TEST_2);
		File file2 = testRepo.createFile(iProject, name + File.separator
				+ "keep");
		RevCommit folderCommit = testRepo.addAndCommit(iProject, file2,
				"initial commit");
		GitFolderResourceVariant remote = new GitFolderResourceVariant(repo,
				folderCommit, path);
