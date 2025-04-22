		}
	}

	public void setTreeProviders(ITreeContentProvider contentProvider,
			ILabelProvider labelProvider) {
		treeViewer.setContentProvider(contentProvider);
		treeViewer.setLabelProvider(labelProvider);
	}

	public void setTreeComparator(ViewerComparator comparator) {
		treeViewer.setComparator(comparator);
	}

	protected void setWhiteChecked(Object treeElement, boolean isWhiteChecked) {
		if (isWhiteChecked) {
			if (!whiteCheckedTreeItems.contains(treeElement)) {
