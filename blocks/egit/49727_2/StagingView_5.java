		updateColumnWidths();
	}

	private void updateColumnWidths() {
		updateColumnWidths(unstagedViewer);
		updateColumnWidths(stagedViewer);
	}

	private void updateColumnWidths(TreeViewer treeViewer) {
		Tree tree = treeViewer.getTree();
		tree.getColumn(1).pack();
		int availableWidth = tree.getClientArea().width - tree.getColumn(1).getWidth();
		if (availableWidth < 200) {
			availableWidth = 200;
		}
		tree.getColumn(0).setWidth(availableWidth);
