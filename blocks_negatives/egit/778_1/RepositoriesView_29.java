	public boolean show(ShowInContext context) {
		ISelection selection = context.getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) selection;
			if (ss.size() == 1) {
				Object element = ss.getFirstElement();
				if (element instanceof IAdaptable) {
					IResource resource = (IResource) ((IAdaptable) element)
							.getAdapter(IResource.class);
					if (resource != null) {
						showResource(resource);
						return true;
					}
				}
			}
		}
		return false;
	}

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
