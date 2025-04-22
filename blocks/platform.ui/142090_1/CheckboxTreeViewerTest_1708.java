		treeItems.add(item);
		testElements.add(item.getData());
		TreeItem[] children = item.getItems();
		for (TreeItem element : children) {
			collectElementsInBranch(element, treeItems, testElements);
		}
	}

	private void checkAllStates(String comment, CheckboxTreeViewer ctv, int shift) {
