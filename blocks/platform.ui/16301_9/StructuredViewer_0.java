	public IStructuredSelection getStructuredSelection() {
		ISelection selection = getSelection();
		if (selection instanceof IStructuredSelection) {
			return (IStructuredSelection) selection;

		}
		throw new ClassCastException(
				"StructuredViewer should return an instance of IStructuredSelection from its getSelection() method. "); //$NON-NLS-1$
	}

