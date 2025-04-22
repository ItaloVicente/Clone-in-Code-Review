	}

	protected void handleDoubleClick(DoubleClickEvent event) {
		IStructuredSelection selection = (IStructuredSelection) event.getSelection();
		Object element = selection.getFirstElement();

		TreeViewer viewer = getTreeViewer();
		if (viewer.isExpandable(element)) {
			viewer.setExpandedState(element, !viewer.getExpandedState(element));
