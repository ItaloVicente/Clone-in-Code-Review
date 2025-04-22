		return getProjectsInRepositoryOfSelectedResources(selection);
	}

	/**
	 * List the projects with selected resources, if all projects are connected
	 * to a Git repository.
	 *
	 * @param selection
	 *
	 * @return the tracked projects affected by the current resource selection
	 */
	private IProject[] getProjectsInRepositoryOfSelectedResources(
			IStructuredSelection selection) {
		Set<IProject> ret = new LinkedHashSet<IProject>();
		Repository[] repositories = getRepositoriesFor(getProjectsForSelectedResources(selection));
		final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();
		for (IProject project : projects) {
			RepositoryMapping mapping = RepositoryMapping.getMapping(project);
			for (Repository repository : repositories)
				if (mapping != null && mapping.getRepository() == repository) {
					ret.add(project);
					break;
