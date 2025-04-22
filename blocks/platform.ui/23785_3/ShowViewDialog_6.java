	public void doubleClick(DoubleClickEvent event) {
		IStructuredSelection s = (IStructuredSelection) event.getSelection();
		Object element = s.getFirstElement();
		if (filteredTree.getViewer().isExpandable(element)) {
			filteredTree.getViewer().setExpandedState(element,
					!filteredTree.getViewer().getExpandedState(element));
		} else if (viewDescs.length > 0) {
			saveWidgetValues();
			setReturnCode(OK);
			close();
