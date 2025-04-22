		IResource remoteResource = createMock(IResource.class);
		expect(remoteResource.exists()).andReturn(true);
		replay(remoteResource);
		GitBlobResourceVariant remote = new GitBlobResourceVariant(
				remoteResource,
				repo,
				ObjectId.fromString("0123456789012345678901234567890123456789"),
				null);
