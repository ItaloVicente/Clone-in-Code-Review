		File file = testRepo.createFile(iProject, name);
		RevCommit revCommit = testRepo.addAndCommit(iProject, file,
				"initial commit");
		String path = Repository.stripWorkDir(repo.getWorkDir(), file);
		GitFolderResourceVariant remote = new GitFolderResourceVariant(repo,
				revCommit, path);

