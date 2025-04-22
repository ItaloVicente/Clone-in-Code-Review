		testRepo.addToIndex(iProject, file);
		RevCommit remoteCommit = testRepo.commit("second commit");
		GitBlobResourceVariant remote = new GitBlobResourceVariant(repo,
				remoteCommit, repoRelativePath);

		localBytes[8120] = 'b';

