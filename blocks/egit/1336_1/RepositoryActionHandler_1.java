	protected IProject[] getProjectsForSelectedResources(ExecutionEvent event)
			throws ExecutionException {
		IStructuredSelection selection = getSelection(event);
		return getProjectsForSelectedResources(selection);
	}


