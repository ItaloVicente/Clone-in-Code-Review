		IResource baseResource = createMock(IResource.class);
		expect(baseResource.exists()).andReturn(true);
		expect(baseResource.getFullPath()).andReturn(path);
		replay(baseResource);
		GitFolderResourceVariant base = new GitFolderResourceVariant(
				baseResource);

		IResource remoteResource = createMock(IResource.class);
		expect(remoteResource.exists()).andReturn(true);
		expect(remoteResource.getFullPath()).andReturn(path);
		replay(remoteResource);
		GitFolderResourceVariant remote = new GitFolderResourceVariant(
				remoteResource);
