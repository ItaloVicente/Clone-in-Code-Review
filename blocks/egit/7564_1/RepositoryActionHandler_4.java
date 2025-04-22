	protected IPath[] getSelectedLocations(ExecutionEvent event)
			throws ExecutionException {
		IStructuredSelection selection = getSelection(event);
		return getSelectedLocations(selection);
	}

