		ISelection selection = page.getSelectionProvider().getSelection();
		return getStructuredSelection(selection);
	}

	protected IStructuredSelection getSelection(ExecutionEvent event)
			throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelectionChecked(event);
		return getStructuredSelection(selection);
	}

	private IStructuredSelection getStructuredSelection(ISelection selection) {
		if (selection instanceof IStructuredSelection)
			return (IStructuredSelection) selection;
