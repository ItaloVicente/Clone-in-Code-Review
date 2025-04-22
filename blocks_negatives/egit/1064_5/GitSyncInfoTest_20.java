		stage(fileName, localBytes);
		RevCommit firstCommit = commit();
		ObjectId objectId = stage(fileName, localBytes);
		RevCommit secondCommit = commit();
		RevCommitList<RevCommit> baseCommits = new RevCommitList<RevCommit>();
		baseCommits.add(firstCommit);
		baseCommits.add(secondCommit);

		IFile baseResource = createMock(IFile.class);
		replay(baseResource);
		GitBlobResourceVariant base = new GitBlobResourceVariant(baseResource,
				repo, objectId, baseCommits);
