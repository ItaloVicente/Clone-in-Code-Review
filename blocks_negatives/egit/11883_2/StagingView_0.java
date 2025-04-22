	private IFile getResource(String path) {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IFile file = root.getFileForLocation(new Path(path));
		if (file == null)
			return null;
		if (file.getProject().isAccessible())
			return file;
		return null;
	}

