	private void removeRepository(final IProgressMonitor monitor,
			final Repository... repository) {
		final List<IProject> projectsToDelete = new ArrayList<IProject>();

		monitor
				.setTaskName(UIText.RepositoriesView_DeleteRepoDeterminProjectsMessage);

		for (Repository repo : repository) {
			File workDir = repo.getWorkDir();
			final IPath wdPath = new Path(workDir.getAbsolutePath());
			for (IProject prj : ResourcesPlugin.getWorkspace().getRoot()
					.getProjects()) {
				if (monitor.isCanceled())
					return;
				if (wdPath.isPrefixOf(prj.getLocation())) {
					projectsToDelete.add(prj);
				}
			}
		}

		if (!projectsToDelete.isEmpty()) {
			boolean confirmed;
			confirmed = confirmProjectDeletion(projectsToDelete);
			if (!confirmed) {
				return;
			}
		}

		if (monitor.isCanceled())
			return;

		IWorkspaceRunnable wsr = new IWorkspaceRunnable() {

			public void run(IProgressMonitor actMonitor) throws CoreException {

				for (IProject prj : projectsToDelete) {
					prj.delete(false, false, actMonitor);
				}
				for (Repository repo : repository)
					removeDir(repo.getDirectory());
				scheduleRefresh();
			}
		};

		try {
			ResourcesPlugin.getWorkspace().run(wsr,
					ResourcesPlugin.getWorkspace().getRoot(),
					IWorkspace.AVOID_UPDATE, monitor);
		} catch (CoreException e1) {
			Activator.logError(e1.getMessage(), e1);
		}
	}
