		IPath fullPath = resource.getFullPath().removeLastSegments(1);
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();

		for (IProject project : projects) {
			if (isNonWorkspace(project))
				continue;
			RepositoryMapping mapping = getMapping(project);
			if (mapping == null)
				continue;

			Path workingTree = new Path(mapping.getWorkTree().toString());
			IPath relative = fullPath.makeRelativeTo(workingTree);
			String firstSegment = relative.segment(0);

				return mapping;
		}

		return null;
