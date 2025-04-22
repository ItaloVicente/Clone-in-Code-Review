		IPath remotePath = createMock(IPath.class);
		replay(remotePath);
		IResource remoteResource = createMock(IResource.class);
		expect(remoteResource.getFullPath()).andReturn(remotePath);
		replay(remoteResource);
		GitFolderResourceVariant remote = new GitFolderResourceVariant(
				remoteResource);
