		stage(fileName, localBytes);
		RevCommit firstCommit = commit();
		byte[] remoteBytes = new byte[localBytes.length];
		System.arraycopy(localBytes, 0, remoteBytes, 0, localBytes.length);
		remoteBytes[8100] = 'b';
		ObjectId objectId = stage(fileName, remoteBytes);
		RevCommit secondCommit = commit();
		RevCommitList<RevCommit> baseCommits = new RevCommitList<RevCommit>();
		baseCommits.add(firstCommit);
		baseCommits.add(secondCommit);

		IFile baseResource = createMock(IFile.class);
		replay(baseResource);
		GitBlobResourceVariant base = new GitBlobResourceVariant(baseResource,
				repo, objectId, baseCommits);
