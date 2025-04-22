	public IResource getResource() {
		String absoluteFilePath = getRepository().getWorkTree()
				.getAbsolutePath() + "/" + gitPath; //$NON-NLS-1$
		IFile file = ResourcesPlugin.getWorkspace().getRoot()
				.getFileForLocation(new Path(absoluteFilePath));
		return file;
	}

