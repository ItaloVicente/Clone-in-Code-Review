
	public void refreshView() {
		if ((treeViewer.getControl().getStyle() & SWT.VIRTUAL) == 0) {
			treeViewer.refresh();
			return;
		}

		internalRedraw();
	}

	@Override
	public void updateElement(Object parent, int index) {
		Object[] children = getChildren(parent);
		if (index < children.length) {
			treeViewer.replace(parent, index, children[index]);
		}
		treeViewer.setHasChildren(parent, children.length > 0);
	}

	@Override
	public void updateChildCount(Object element, int currentChildCount) {
		Object[] children = getChildren(element);
		int newChildCount = children.length;
		if (newChildCount != currentChildCount) {
			treeViewer.setChildCount(element, newChildCount);
		}
	}

	private void internalRedraw() {
		if ((treeViewer.getControl().getStyle() & SWT.VIRTUAL) == 0) {
			return;
		}
		Tree tree = treeViewer.getTree();
		tree.setItemCount(0);
		tree.setItemCount(getChildren(null).length);
	}

