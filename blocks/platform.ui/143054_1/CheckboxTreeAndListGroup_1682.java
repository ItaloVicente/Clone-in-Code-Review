	}

	public void setListProviders(IStructuredContentProvider contentProvider,
			ILabelProvider labelProvider) {
		listViewer.setContentProvider(contentProvider);
		listViewer.setLabelProvider(labelProvider);
	}

	public void setListComparator(ViewerComparator comparator) {
		listViewer.setComparator(comparator);
	}

	public void setRoot(Object newRoot) {
		this.root = newRoot;
		initialize();
	}

	protected void setTreeChecked(Object treeElement, boolean state) {

		if (treeElement.equals(currentTreeSelection)) {
			listViewer.setAllChecked(state);
		}

		if (state) {
			List listItemsChecked = new ArrayList();
