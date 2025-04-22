	}

	private void grayUpdateHierarchy(Object treeElement) {
		boolean shouldBeAtLeastGray = determineShouldBeAtLeastGrayChecked(treeElement);
		treeViewer.setGrayChecked(treeElement, shouldBeAtLeastGray);
		if (whiteCheckedTreeItems.contains(treeElement)) {
