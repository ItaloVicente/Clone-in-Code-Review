		File file = testRepo.createFile(iProject, fileName);
		RevCommit baseCommit = testRepo.appendContentAndCommit(iProject, file,
				localBytes, "initial commit");
		String repoRelPath = Repository.stripWorkDir(repo.getWorkTree(), file);
		GitBlobResourceVariant base = new GitBlobResourceVariant(repo,
				baseCommit, repoRelPath);
