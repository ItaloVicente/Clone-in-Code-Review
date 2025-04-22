
	protected IStructuredSelection getSelection(ReflogView view) {
		ISelection pageSelection = view.getSelectionProvider().getSelection();
		if (pageSelection instanceof IStructuredSelection)
			return (IStructuredSelection) pageSelection;
		else
			return new StructuredSelection();
	}

