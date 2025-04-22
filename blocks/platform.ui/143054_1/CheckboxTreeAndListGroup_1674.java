	}

	public void expandAll() {
		treeViewer.expandAll();
	}

	public Iterator getAllCheckedListItems() {
		List result = new ArrayList();
		Iterator listCollectionsEnum = checkedStateStore.values().iterator();

		while (listCollectionsEnum.hasNext()) {
			Iterator currentCollection = ((List) listCollectionsEnum.next())
					.iterator();
			while (currentCollection.hasNext()) {
