		File file = testRepo.createFile(iProject, "test" + File.separator
				+ "keep");
		RevCommit commit = testRepo.addAndCommit(iProject, file,
				"initial commit");
		String filePath = Repository.stripWorkDir(repo.getWorkDir(), file);
		String folderPath = Repository.stripWorkDir(repo.getWorkDir(),
				new File(file.getParent()));
		GitBlobResourceVariant base = new GitBlobResourceVariant(repo,
				commit, filePath);
		GitFolderResourceVariant remote = new GitFolderResourceVariant(repo,
				commit, folderPath);
