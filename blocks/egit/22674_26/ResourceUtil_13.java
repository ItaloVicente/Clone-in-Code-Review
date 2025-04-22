		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		for (IProject project : root.getProjects()) {
			if (RepositoryProvider.getProvider(project, GitProvider.ID) != null) {
				final IPath projectLocation = project.getLocation();
				if (projectLocation != null && projectLocation.isPrefixOf(path)) {
					final IPath projectRelativePath = path
							.makeRelativeTo(projectLocation);
					if (isFolder) {
						return project.getFolder(projectRelativePath);
					} else {
						return project.getFile(projectRelativePath);
					}
				}
			}
		}
		return null;
