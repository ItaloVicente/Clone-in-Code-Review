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
			repo.removeRepositoryChangedListener(repositoryListener);
		}

		if (!projectsToDelete.isEmpty()) {
			boolean confirmed;
			confirmed = confirmProjectDeletion(projectsToDelete);
			if (!confirmed) {
