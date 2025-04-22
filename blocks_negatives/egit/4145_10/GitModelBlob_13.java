		return new GitCompareInput(getRepository(), ancestorData, remoteData,
				baseData, gitPath);
	}

	public IResource getResource() {
		String absoluteFilePath = getRepository().getWorkTree()
		Path path = new Path(absoluteFilePath);
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IFile file = workspaceRoot.getFileForLocation(path);
