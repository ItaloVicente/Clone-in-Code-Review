		File file = testRepo.createFile(iProject, "test" + File.separator
				+ "keep");
		RevCommit commit = testRepo.addAndCommit(iProject, file,
				"initial commit");
		String path = Repository.stripWorkDir(repo.getWorkDir(), file);

		GitFolderResourceVariant remote = new GitFolderResourceVariant(repo,
				commit, path);
