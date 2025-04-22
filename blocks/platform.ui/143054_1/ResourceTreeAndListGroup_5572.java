			listItemsChecked.add(listItem);
		}
		checkedStateStore.put(treeElement, listItemsChecked);
	}

	public void setListProviders(IStructuredContentProvider contentProvider, ILabelProvider labelProvider) {
		listViewer.setContentProvider(contentProvider);
		listViewer.setLabelProvider(labelProvider);
		listContentProvider = contentProvider;
		listLabelProvider = labelProvider;
	}

	public void setListComparator(ViewerComparator comparator) {
		listViewer.setComparator(comparator);
	}

	public void setRoot(Object newRoot) {
		this.root = newRoot;
		initialize();
	}

	private void setTreeChecked(Object treeElement, boolean state) {
		if (treeElement.equals(currentTreeSelection)) {
			listViewer.setAllChecked(state);
		}
		if (state) {
			setListForWhiteSelection(treeElement);
		} else {
