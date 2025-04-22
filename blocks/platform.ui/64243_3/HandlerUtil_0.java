	public IStructuredSelection getCurrentStructuredSelection(ExecutionEvent event) {
		ISelection selection = getCurrentSelection(event);
		if (selection instanceof IStructuredSelection) {
			return (IStructuredSelection) selection;
		}
		return StructuredSelection.EMPTY;
	}

