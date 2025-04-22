
	private boolean haveVirtualTree() {
		return (treeViewer.getControl().getStyle() & SWT.VIRTUAL) != 0;
	}

	private void internalRedraw() {
		if (!haveVirtualTree()) {
			return;
		}
		Tree tree = treeViewer.getTree();
		tree.setItemCount(0);
		tree.setItemCount(getChildren(null).length);
	}

