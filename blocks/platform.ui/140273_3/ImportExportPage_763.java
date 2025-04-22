	protected abstract Composite createTreeViewer(Composite parent);

	protected void treeDoubleClicked(DoubleClickEvent event) {
		ISelection selection = event.getViewer().getSelection();
		IStructuredSelection ss = (IStructuredSelection) selection;
		listSelectionChanged(ss);
