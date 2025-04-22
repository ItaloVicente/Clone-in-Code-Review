		IResource baseResource = createMock(IResource.class);
		expect(baseResource.exists()).andReturn(true);
		replay(baseResource);
		GitFolderResourceVariant base = new GitFolderResourceVariant(
				baseResource);
		IResource remoteResource = createMock(IResource.class);
		expect(remoteResource.exists()).andReturn(true);
		replay(remoteResource);
		GitBlobResourceVariant remote = new GitBlobResourceVariant(
				remoteResource, repo, ObjectId.zeroId(), null);
