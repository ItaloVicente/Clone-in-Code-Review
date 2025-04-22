	public static void updateProjectsProperties(Repository repository) {
		IProject[] projects = ProjectUtil.getProjects(repository);
		for (IProject project : projects) {
			initializeProperties(project, repository);
		}
	}

	private static void initializeProperties(IProject project, Repository repository) {
		RepositoryState state = repository.getRepositoryState();
		try {
			project.setPersistentProperty(REPOSITORY_STATE_KEY, state.name());
		} catch (CoreException e) {
			Activator.logError(e.getMessage(), e);
		}
	}

