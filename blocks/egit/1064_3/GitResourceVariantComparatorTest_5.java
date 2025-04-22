		File file = testRepo.createFile(iProject, "test" + File.separator
				+ "keep");
		RevCommit commit = testRepo.addAndCommit(iProject, file,
				"initial commit");
		String filePath = Repository.stripWorkDir(repo.getWorkDir(), file);
		String folderPath = Repository.stripWorkDir(repo.getWorkDir(),
				new File(file.getParent()));

		GitFolderResourceVariant base = new GitFolderResourceVariant(repo,
				commit, folderPath);
		GitBlobResourceVariant remote = new GitBlobResourceVariant(repo,
				commit, filePath);
