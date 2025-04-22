		IResource resource = AdapterUtils.adapt(selection.getFirstElement(), IResource.class);
		if (resource != null) {
			if (resource instanceof IFile)
				return isStaged(repository, resource.getLocation());
		} else {
			IPath location = AdapterUtils.adapt(selection.getFirstElement(), IPath.class);
			if (location != null && location.toFile().isFile())
				return isStaged(repository, location);
