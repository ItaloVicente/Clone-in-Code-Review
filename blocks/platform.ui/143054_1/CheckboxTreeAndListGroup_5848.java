		Object parent = treeContentProvider.getParent(treeElement);
		if (parent != null) {
			updateHierarchy(parent);
		}
	}

	public void updateSelections(final Map items) {

		BusyIndicator.showWhile(treeViewer.getControl().getDisplay(),
				() -> {
