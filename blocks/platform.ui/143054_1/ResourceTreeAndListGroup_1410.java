	}

	private void treeItemChecked(Object treeElement, boolean state) {
		setTreeChecked(treeElement, state);
		Object parent = treeContentProvider.getParent(treeElement);
		if (parent == null || parent instanceof IWorkspaceRoot) {
