
		projects = new HashSet<IProject>();
		final IProject[] workspaceProjects = ResourcesPlugin.getWorkspace()
				.getRoot().getProjects();
		for (IProject project : workspaceProjects) {
			RepositoryMapping mapping = RepositoryMapping.getMapping(project);
			if (mapping != null && mapping.getRepository() == repo)
				projects.add(project);
		}

