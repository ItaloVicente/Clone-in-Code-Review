		if (!ms.isOK()) {
			throw new CoreException(ms);
		}
	}

	private void connectProject(Entry<IProject, File> entry, MultiStatus ms,
			IProgressMonitor monitor) throws CoreException {
		IProject project = entry.getKey();

		String taskName = NLS.bind(
				CoreText.ConnectProviderOperation_ConnectingProject,
				project.getName());
		monitor.setTaskName(taskName);

		if (GitTraceLocation.CORE.isActive()) {
			GitTraceLocation.getTrace()
					.trace(GitTraceLocation.CORE.getLocation(), taskName);
		}

		RepositoryFinder finder = new RepositoryFinder(project);
		finder.setFindInChildren(false);
		Collection<RepositoryMapping> repos = finder
				.find(new SubProgressMonitor(monitor, 40));
		if (repos.isEmpty()) {
			ms.add(Activator.error(NLS.bind(
					CoreText.ConnectProviderOperation_NoRepositoriesError,
					project.getName()), null));
			monitor.worked(60);
			return;
		}
		RepositoryMapping actualMapping = findActualRepository(repos,
				entry.getValue());
		if (actualMapping == null) {
			ms.add(Activator.error(NLS.bind(
					CoreText.ConnectProviderOperation_UnexpectedRepositoryError,
					new Object[] { project.getName(),
							entry.getValue().toString(), repos.toString() }),
					null));

			monitor.worked(60);
			return;
		}
		GitProjectData projectData = new GitProjectData(project);
		try {
			projectData.setRepositoryMappings(Arrays.asList(actualMapping));
			projectData.store();
			GitProjectData.add(project, projectData);
		} catch (CoreException ce) {
			ms.add(ce.getStatus());
			deleteGitProvider(ms, project);
			return;
		} catch (RuntimeException ce) {
			ms.add(Activator.error(ce.getMessage(), ce));
			deleteGitProvider(ms, project);
			return;
		}
		RepositoryProvider.map(project, GitProvider.ID);
		autoIgnoreDerivedResources(project, monitor);
		project.refreshLocal(IResource.DEPTH_INFINITE,
				new SubProgressMonitor(monitor, 50));
		monitor.worked(10);
	}

	private void deleteGitProvider(MultiStatus ms, IProject project) {
		try {
			GitProjectData.delete(project);
		} catch (IOException e) {
			ms.add(Activator.error(e.getMessage(), e));
		}
