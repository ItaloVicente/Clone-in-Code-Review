		File file1 = testRepo.createFile(iProject, "test1" + File.separator
				+ "keep1");
		testRepo.track(file1);
		testRepo.addToIndex(testRepo.getIFile(iProject, file1));
		RevCommit commit = testRepo.commit("initial commit");

		String path1 = Repository.stripWorkDir(repo.getWorkTree(), new File(
				file1.getParent()));

		GitFolderResourceVariant base = new GitFolderResourceVariant(repo,
				commit, path1);
		GitFolderResourceVariant remote = new GitFolderResourceVariant(repo,
				commit, path1);
