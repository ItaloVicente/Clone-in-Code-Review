		File file = testRepo.createFile(iProject, "test" + File.separator
				+ ".keep");
		RevCommit commit = testRepo.addAndCommit(iProject, file,
				"initial commit");

		File fullPath = new File(file.getParent());
		String path = Repository.stripWorkDir(repo.getWorkTree(), fullPath);
		GitFolderResourceVariant base = new GitFolderResourceVariant(repo,
				commit, path);
		GitFolderResourceVariant remote = new GitFolderResourceVariant(repo,
				commit, path);
