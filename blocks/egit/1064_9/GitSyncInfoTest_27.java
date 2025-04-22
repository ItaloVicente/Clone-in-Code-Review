		File file = testRepo.createFile(iProject, name);
		RevCommit revCommit = testRepo.addAndCommit(iProject, file,
				"initial commit");
		String path = Repository.stripWorkDir(repo.getWorkTree(), file);
		GitFolderResourceVariant base = new GitFolderResourceVariant(repo,
				revCommit, path);
