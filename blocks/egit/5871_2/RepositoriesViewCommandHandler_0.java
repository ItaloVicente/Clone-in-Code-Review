	public List<T> getSelectedNodes(ExecutionEvent event)
			throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelectionChecked(event);
		if (selection instanceof IStructuredSelection)
			return ((IStructuredSelection) selection).toList();
		else
			return Collections.emptyList();
