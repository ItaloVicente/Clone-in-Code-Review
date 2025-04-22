	private void buildIndexHeadDiffList(IProject[] selectedProjects, IProgressMonitor monitor)
			throws IOException, OperationCanceledException {
		HashMap<Repository, HashSet<IProject>> repositories = new HashMap<Repository, HashSet<IProject>>();

		for (IProject project : selectedProjects) {
			RepositoryMapping repositoryMapping = RepositoryMapping
					.getMapping(project);
			assert repositoryMapping != null;

			Repository repository = repositoryMapping.getRepository();

			HashSet<IProject> projects = repositories.get(repository);

			if (projects == null) {
				projects = new HashSet<IProject>();
				repositories.put(repository, projects);
			}

			projects.add(project);
		}
