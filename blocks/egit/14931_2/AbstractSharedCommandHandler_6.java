	protected Ref getRef(ExecutionEvent event) throws ExecutionException {
		ISelection currentSelection = getCurrentSelectionChecked(event);
		if (currentSelection instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) currentSelection;
			Object selected = selection.getFirstElement();
			return getRef(selected);
		} else
			return null;
	}

