	public static RepositoryMapping findRepositoryMapping(Repository repository) {
		final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();
		for (IProject project : projects) {
			RepositoryMapping mapping = RepositoryMapping.getMapping(project);
			if (mapping != null && mapping.getRepository() == repository)
				return mapping;
		}
		return null;
	}

