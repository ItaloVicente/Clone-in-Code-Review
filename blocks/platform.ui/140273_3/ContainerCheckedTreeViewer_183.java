	protected void doCheckStateChanged(Object element) {
		Widget item = findItem(element);
		if (item instanceof TreeItem) {
			TreeItem treeItem = (TreeItem) item;
			treeItem.setGrayed(false);
			updateChildrenItems(treeItem);
			updateParentItems(treeItem.getParentItem());
		}
	}
