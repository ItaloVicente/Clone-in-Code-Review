		IFile baseResource = createMock(IFile.class);
		replay(baseResource);
		GitBlobResourceVariant base = new GitBlobResourceVariant(baseResource,
				repo, objectId, baseCommits); // baseComits has two entry

		IResource remoteResource = createMock(IResource.class);
		replay(remoteResource);
		RevCommitList<RevCommit> remoteCommits = new RevCommitList<RevCommit>();
		remoteCommits.add(firstCommit);
		GitBlobResourceVariant remote = new GitBlobResourceVariant(
				remoteResource,
				repo,
				ObjectId.fromString("0123456789012345678901234567890123456789"),
