		createProjects(projectsToCreate, false, repository,
				selectedWorkingSets, monitor);
	}

	public static void createProjects(
			final Set<ProjectRecord> projectsToCreate, final boolean open,
			final Repository repository,
			final IWorkingSet[] selectedWorkingSets, IProgressMonitor monitor)
			throws InvocationTargetException, InterruptedException {
