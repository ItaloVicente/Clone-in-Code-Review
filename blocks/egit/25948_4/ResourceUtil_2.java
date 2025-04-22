		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		for (IProject project : root.getProjects()) {
			if (repository.equals(RepositoryProvider.getProvider(project,
					GitProvider.ID))) {
				final IPath projectLocation = project.getLocation();
				if (projectLocation != null && projectLocation.isPrefixOf(path)) {
					final IPath projectRelativePath = path
							.makeRelativeTo(projectLocation);
					if (FileMode.TREE.equals(fileMode))
						return project.getFolder(projectRelativePath);
					else if (FileMode.REGULAR_FILE.equals(fileMode))
						return project.getFile(projectRelativePath);
				}
			}
		}
		return null;
