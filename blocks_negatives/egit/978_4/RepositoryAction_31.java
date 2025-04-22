	/**
	 * Figure out which repositories to use. All selected
	 * resources must map to a Git repository.
	 *
	 * @return repository for current project, or null
	 */
	protected Repository[] getRepositories() {
		IProject[] selectedProjects = getSelectedProjects();
		Set<Repository> repos = new HashSet<Repository>(selectedProjects.length);
		for (IProject project : selectedProjects) {
			RepositoryMapping repositoryMapping = RepositoryMapping.getMapping(project);
			if (repositoryMapping == null)
				return new Repository[0];
			repos.add(repositoryMapping.getRepository());
