
	public static void createProjects(final Repository repository,
			final IProgressMonitor monitor) throws CoreException {
		List<File> projects = new ArrayList<File>();
		if (ProjectUtil.findProjectFiles(projects, repository.getWorkTree()
				.getParentFile(), null, monitor))
			for (File file : projects)
				ProjectUtil.createProject(repository, file, monitor);
	}

	public static void createProject(final Repository repository,
			final File file, final IProgressMonitor monitor)
			throws CoreException {
		final ProjectRecord record = new ProjectRecord(file);
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		String projectName = record.getProjectName();
		final IProject project = workspace.getRoot().getProject(projectName);
		if (project.exists())
			return;
		if (record.getProjectDescription() == null) {
			record.setProjectDescription(workspace
					.newProjectDescription(projectName));
			IPath locationPath = new Path(record.getProjectSystemFile()
					.getAbsolutePath());

			if (Platform.getLocation().isPrefixOf(locationPath))
				record.getProjectDescription().setLocation(null);
			else
				record.getProjectDescription().setLocation(locationPath);
		} else
			record.getProjectDescription().setName(projectName);

		monitor.subTask(NLS.bind(CoreText.ProjectUtil_taskImportingProject,
				file.getPath()));
		try {
			project.create(record.getProjectDescription(),
					new SubProgressMonitor(monitor, 30));
			project.open(IResource.BACKGROUND_REFRESH, new SubProgressMonitor(
					monitor, 50));
		} finally {
			monitor.done();
		}
		new ConnectProviderOperation(project, repository.getDirectory())
				.execute(monitor);
	}
