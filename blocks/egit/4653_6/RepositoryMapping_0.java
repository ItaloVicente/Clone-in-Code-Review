
	private static RepositoryMapping getMappingForNonWorkspaceResource(
			final IResource resource) {
		IPath fullPath = resource.getFullPath().removeLastSegments(1);
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();

		for (IProject project : projects) {
			RepositoryMapping mapping = getMapping(project);
			if (mapping == null)
				continue;

			Path workingTree = new Path(mapping.getWorkTree().toString());
			IPath relative = fullPath.makeRelativeTo(workingTree);
			String firstSegment = relative.segment(0);

			if (firstSegment == null || !"..".equals(firstSegment)) //$NON-NLS-1$
				return mapping;
		}

		return null;
	}

