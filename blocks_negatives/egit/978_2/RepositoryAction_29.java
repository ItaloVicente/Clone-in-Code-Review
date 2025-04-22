	/**
	 * List the projects with selected resources, if all projects are connected
	 * to a Git repository.
	 *
	 * @return the tracked projects affected by the current resource selection
	 */
	public IProject[] getProjectsInRepositoryOfSelectedResources() {
		Set<IProject> ret = new HashSet<IProject>();
		Repository[] repositories = getRepositoriesFor(getProjectsForSelectedResources());
		final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (IProject project : projects) {
			RepositoryMapping mapping = RepositoryMapping.getMapping(project);
			for (Repository repository : repositories) {
				if (mapping != null && mapping.getRepository() == repository) {
					ret.add(project);
					break;
				}
			}
		}
		return ret.toArray(new IProject[ret.size()]);
