		return parentName.append(elementText).toString();
	}

	private int getListItemsSize(Object treeElement) {
		Object[] elements = listContentProvider.getElements(treeElement);
		return elements.length;
	}


	private void grayCheckHierarchy(Object treeElement) {
		expandTreeElement(treeElement);
		if (checkedStateStore.containsKey(treeElement)) {
