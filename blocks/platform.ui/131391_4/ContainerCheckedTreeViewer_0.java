    private void doCheckStateChanged(Object[] elements) {
    	HashSet<TreeItem> parents = new HashSet<>();
		for (Object element : elements) {
			Widget item = findItem(element);
			if (item instanceof TreeItem) {
				TreeItem treeItem = (TreeItem) item;
				treeItem.setGrayed(false);
				updateChildrenItems(treeItem);
				TreeItem parentItem = treeItem.getParentItem();
				if (parentItem != null) {
					parents.add(parentItem);
				}
			}
		}
		for (TreeItem parent : parents) {
			updateParentItems(parent);
		}
    }

