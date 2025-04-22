	protected IStructuredSelection getSelection(ExecutionEvent event)
			throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelectionChecked(event);
		if (selection instanceof IStructuredSelection) {
			return (IStructuredSelection) selection;
		}
		return new StructuredSelection();
	}

