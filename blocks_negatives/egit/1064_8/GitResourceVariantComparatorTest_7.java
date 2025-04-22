		IResource baseResource = createMock(IResource.class);
		expect(baseResource.exists()).andReturn(true);
		replay(baseResource);
		GitBlobResourceVariant base = new GitBlobResourceVariant(
				baseResource,
				repo,
				ObjectId.fromString("0123456789012345678901234567890123456789"),
				null);
