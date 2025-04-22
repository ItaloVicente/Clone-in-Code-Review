		GitBlobResourceVariant base = new GitBlobResourceVariant(repo,
				baseCommit, repoRelativePath);

		testRepo.addToIndex(iProject, file);
		RevCommit remoteCommit = testRepo.commit("second commit");
		GitBlobResourceVariant remote = new GitBlobResourceVariant(repo,
				remoteCommit, repoRelativePath);
