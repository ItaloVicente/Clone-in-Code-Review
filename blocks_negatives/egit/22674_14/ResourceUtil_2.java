		final IResource resource;
		if (path.segmentCount() > 1)
			resource = workspaceRoot.getFile(path);
		else
			resource = workspaceRoot.getProject(path.toString());
		return resource;
