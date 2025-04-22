	public static IProject[] getProjects(Repository repository) {
		List<IProject> result = new ArrayList<IProject>();
		final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();
		for (IProject project : projects) {
			if (project.isAccessible()) {
				RepositoryMapping mapping = RepositoryMapping
						.getMapping(project);
				if (mapping != null && mapping.getRepository() == repository)
					result.add(project);
			}
		}
		return result.toArray(new IProject[result.size()]);
	}

