		if (fullPath == null) {
			IResource resource;
			IPath location = new Path(repo.getWorkTree() + File.separator
					+ path);

			if (isContainer())
				resource = workspaceRoot.getContainerForLocation(location);
			else
				resource = workspaceRoot.getFileForLocation(location);

			if (resource != null)
				fullPath = resource.getFullPath();
			else
				fullPath = new Path(path);
		}
