		}

		return result.iterator();
	}

	public Set getAllCheckedTreeItems() {
		return checkedStateStore.keySet();
	}

	public int getCheckedElementCount() {
		return checkedStateStore.size();
	}

	protected int getListItemsSize(Object treeElement) {
		Object[] elements = listContentProvider.getElements(treeElement);
		return elements.length;
	}

	public Table getListTable() {
		return this.listViewer.getTable();
	}

	protected void grayCheckHierarchy(Object treeElement) {

		if (checkedStateStore.containsKey(treeElement)) {
