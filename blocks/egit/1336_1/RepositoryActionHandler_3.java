		IStructuredSelection selection = getSelection(event);
		Shell shell = getShell(event);
		return getRepository(warn, selection, shell);
	}

	protected Repository getRepository() {
		IStructuredSelection selection = getSelection();
		return getRepository(false, selection, null);
	}

	private Repository getRepository(boolean warn, IStructuredSelection selection, Shell shell) {
