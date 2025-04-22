		File file1 = testRepo.createFile(iProject, "test1" + File.separator
				+ "keep1");
		File file2 = testRepo.createFile(iProject, "test2" + File.separator
				+ "keep2");
		testRepo.track(file1);
		testRepo.track(file2);
		testRepo.addToIndex(testRepo.getIFile(iProject, file1));
		testRepo.addToIndex(testRepo.getIFile(iProject, file2));
		RevCommit commit = testRepo.commit("initial commit");

		String path1 = Repository.stripWorkDir(repo.getWorkTree(), new File(
				file1.getParent()));
		String path2 = Repository.stripWorkDir(repo.getWorkTree(), new File(
				file2.getParent()));

		GitFolderResourceVariant base = new GitFolderResourceVariant(repo,
				commit, path1);
		GitFolderResourceVariant remote = new GitFolderResourceVariant(repo,
				commit, path2);
