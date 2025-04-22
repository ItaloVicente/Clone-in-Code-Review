		IFile baseResource = createMock(IFile.class);
		replay(baseResource);
		GitBlobResourceVariant base = new GitBlobResourceVariant(baseResource,
				repo, null, null);
		IResource remoteResource = createMock(IResource.class);
		replay(remoteResource);
		GitFolderResourceVariant remote = new GitFolderResourceVariant(
				remoteResource);
