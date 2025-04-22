	public static IResource getResourceForLocation(IPath location) {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IFile file = root.getFileForLocation(location);
		if (file != null)
			return file;
		return root.getContainerForLocation(location);
	}

