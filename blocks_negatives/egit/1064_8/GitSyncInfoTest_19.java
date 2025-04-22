		ObjectId objectId = stage(fileName, localBytes);
		RevCommit firstCommit = commit();
		RevCommitList<RevCommit> baseCommits = new RevCommitList<RevCommit>();
		baseCommits.add(firstCommit);

		IFile baseResource = createMock(IFile.class);
		replay(baseResource);
		GitBlobResourceVariant base = new GitBlobResourceVariant(baseResource,
				repo, objectId, baseCommits);

		IResource remoteResource = createMock(IResource.class);
		replay(remoteResource);

		stage(fileName, localBytes);
		RevCommit secondCommit = commit();
		RevCommitList<RevCommit> remoteCommits = new RevCommitList<RevCommit>();
		remoteCommits.add(secondCommit);
		GitBlobResourceVariant remote = new GitBlobResourceVariant(
				remoteResource,
				repo,
				ObjectId.fromString("0123456789012345678901234567890123456789"),
				remoteCommits);
