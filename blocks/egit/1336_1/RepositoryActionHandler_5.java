		IStructuredSelection selection = getSelection(event);
		return getSelectedProjects(selection);
	}

	private IProject[] getSelectedProjects(IStructuredSelection selection) {
		IResource[] selectedResources = getSelectedResources(selection);
