	public IStructuredSelection getStructuredSelection() throws ClassCastException {
		ISelection selection = getSelection();
		if (selection instanceof IStructuredSelection) {
			return (IStructuredSelection) selection;
		}
		throw new ClassCastException(
				"ISelection is not an instance of IStructuredSelection."); //$NON-NLS-1$
	}

