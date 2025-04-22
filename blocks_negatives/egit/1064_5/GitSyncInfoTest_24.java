		IResource baseResource = createMock(IResource.class);
		expect(baseResource.exists()).andReturn(false);
		replay(baseResource);
		GitFolderResourceVariant base = new GitFolderResourceVariant(
				baseResource);

		IResource remoteResource = createMock(IResource.class);
		expect(remoteResource.exists()).andReturn(true);
		replay(remoteResource);
		GitFolderResourceVariant remote = new GitFolderResourceVariant(
				remoteResource);
