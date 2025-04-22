			if (resource instanceof IFile)
				return isStaged(repository, resource.getLocation());
		} else {
			IPath location = AdapterUtils.adapt(selection.getFirstElement(), IPath.class);
			if (location != null && !location.toFile().isDirectory())
				return isStaged(repository, location);
