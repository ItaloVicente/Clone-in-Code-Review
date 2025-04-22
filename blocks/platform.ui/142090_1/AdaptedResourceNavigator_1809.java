		Object element = selection.getFirstElement();

		if (viewer.isExpandable(element)) {
			viewer.setExpandedState(element, !viewer.getExpandedState(element));
		}

	}

	protected void handleSelectionChanged(SelectionChangedEvent event) {
