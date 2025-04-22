		Path path = new Path(absoluteFilePath);
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IFile file = workspaceRoot.getFileForLocation(path);

		if (file == null)
			file = workspaceRoot.getFile(path);

