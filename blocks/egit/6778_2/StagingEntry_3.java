	public IFile getFile() {
		IPath absolutePath = new Path(repository.getWorkTree().getAbsolutePath()).append(path);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IFile resource = root.getFileForLocation(absolutePath);
		if (resource == null)
			resource = root.getFile(absolutePath);
		return resource;
	}

