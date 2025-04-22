		File file = testRepo.createFile(iProject, "test-file");
		RevCommit baseCommit = testRepo.appendContentAndCommit(iProject, file,
				"a", "initial commit");
		RevCommit remoteCommit = testRepo.appendContentAndCommit(iProject,
				file, "bc", "second commit");

		String path = Repository.stripWorkDir(repo.getWorkTree(), file);
		GitBlobResourceVariant base = new GitBlobResourceVariant(repo,
				baseCommit, path);
