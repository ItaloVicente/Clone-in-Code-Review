		IResource baseResource = createMock(IResource.class);
		expect(baseResource.exists()).andReturn(true);
		replay(baseResource);
		GitFolderResourceVariant base = new GitFolderResourceVariant(
				baseResource);

		IResource remoteResource = createMock(IResource.class);
		expect(remoteResource.exists()).andReturn(false);
		replay(remoteResource);
		GitFolderResourceVariant remote = new GitFolderResourceVariant(
				remoteResource);
