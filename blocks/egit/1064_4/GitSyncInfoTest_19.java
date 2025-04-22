		File file = testRepo.createFile(iProject, fileName);
		testRepo.appendFileContent(file, localBytes);
		testRepo.appendFileContent(file, "a");
		RevCommit baseCommit = testRepo.addAndCommit(iProject, file,
				"initial commit");

		String repoRelPath = Repository.stripWorkDir(repo.getWorkDir(), file);
		GitBlobResourceVariant base = new GitBlobResourceVariant(repo,
				baseCommit, repoRelPath);
