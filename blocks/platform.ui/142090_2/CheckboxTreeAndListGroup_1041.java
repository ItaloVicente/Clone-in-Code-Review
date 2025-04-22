	}

	protected boolean areAllChildrenWhiteChecked(Object treeElement) {
		Object[] children = treeContentProvider.getChildren(treeElement);
		for (int i = 0; i < children.length; ++i) {
			if (!whiteCheckedTreeItems.contains(children[i])) {
