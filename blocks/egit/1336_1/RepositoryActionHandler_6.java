		IStructuredSelection selection = getSelection(event);
		return getSelectedResources(selection);
	}

	protected IResource[] getSelectedResources() {
		IStructuredSelection selection = getSelection();
		return getSelectedResources(selection);
	}

	private IResource[] getSelectedResources(IStructuredSelection selection) {
