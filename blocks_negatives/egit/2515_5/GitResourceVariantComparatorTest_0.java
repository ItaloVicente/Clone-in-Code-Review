		IPath localPath = createMock(IPath.class);
		replay(localPath);
		IContainer local = createMock(IContainer.class);
		expect(local.exists()).andReturn(true).times(2);
		expect(local.getFullPath()).andReturn(localPath);
		replay(local);
