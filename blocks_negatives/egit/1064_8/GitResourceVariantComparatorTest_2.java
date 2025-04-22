		IResource baseResource = createMock(IResource.class);
		expect(baseResource.exists()).andReturn(true);
		replay(baseResource);
		GitBlobResourceVariant base = new GitBlobResourceVariant(baseResource,
				repo, ObjectId.zeroId(), null);
		IResource remoteResource = createMock(IResource.class);
		expect(remoteResource.exists()).andReturn(false);
		replay(remoteResource);
		GitBlobResourceVariant remote = new GitBlobResourceVariant(
				remoteResource, repo, ObjectId.zeroId(), null);
