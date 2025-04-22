		IResource baseResource = createMock(IResource.class);
		expect(baseResource.exists()).andReturn(false);
		replay(baseResource);
		GitBlobResourceVariant base = new GitBlobResourceVariant(baseResource,
				repo, ObjectId.zeroId(), null);
		IResource remoteResource = createMock(IResource.class);
		replay(remoteResource);
		GitBlobResourceVariant remote = new GitBlobResourceVariant(
				remoteResource, repo, ObjectId.zeroId(), null);
