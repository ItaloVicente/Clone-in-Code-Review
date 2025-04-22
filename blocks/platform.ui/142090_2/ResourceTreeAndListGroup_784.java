	}

	public void expandAll() {
		treeViewer.expandAll();
	}

	public void collapseAll() {
		treeViewer.collapseAll();
	}

	private void expandTreeElement(final Object item) {
		BusyIndicator.showWhile(treeViewer.getControl().getDisplay(),
				() -> {

					if (expandedTreeNodes.contains(item)) {
