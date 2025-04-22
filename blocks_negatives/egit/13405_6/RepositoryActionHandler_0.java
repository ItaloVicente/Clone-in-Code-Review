	/**
	 * List the projects with selected resources, if all projects are connected
	 * to a Git repository.
	 *
	 * @param event
	 *
	 * @return the tracked projects affected by the current resource selection
	 * @throws ExecutionException
	 */
	protected IProject[] getProjectsInRepositoryOfSelectedResources(
			ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = getSelection(event);
		return getProjectsInRepositoryOfSelectedResources(selection);
	}

