	protected Object getSelectedObject() {
		ISelection selection = viewer.getSelection();
		if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
			IStructuredSelection structured = (IStructuredSelection) selection;
			return structured.getFirstElement();
		}
		return null;
	}

	protected Viewer getViewer() {
		return viewer;
	}

	@Deprecated
