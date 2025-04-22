		File file = testRepo.createFile(iProject, "test" + File.separator
				+ "keep");
		RevCommit commit = testRepo.addAndCommit(iProject, file,
				"initial commit");
		String path = Repository.stripWorkDir(repo.getWorkTree(), file);
		IPath iPath = new Path(path);
