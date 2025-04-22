		IStructuredSelection selection = getSelection(event);
		return getProjectsInRepositoryOfSelectedResources(selection);
	}

	protected IProject[] getProjectsInRepositoryOfSelectedResources() {
		IStructuredSelection selection = getSelection();
		return getProjectsInRepositoryOfSelectedResources(selection);
	}


	private IProject[] getProjectsInRepositoryOfSelectedResources(
			IStructuredSelection selection) {
