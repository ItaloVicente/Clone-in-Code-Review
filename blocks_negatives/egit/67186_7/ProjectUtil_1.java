		final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();
		for (IProject project : projects)
			if (project.isAccessible()) {
				RepositoryMapping mapping = RepositoryMapping
						.getMapping(project);
				if (mapping != null && mapping.getRepository() == repository)
					result.add(project);
