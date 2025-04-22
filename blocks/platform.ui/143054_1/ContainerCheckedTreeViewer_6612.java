	protected void doCheckStateChanged(Object element) {
		Widget item = findItem(element);
		if (item instanceof TreeItem) {
			TreeItem treeItem = (TreeItem) item;
			treeItem.setGrayed(false);
			updateChildrenItems(treeItem);
			updateParentItems(treeItem.getParentItem());
		}
	}

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

	private void initializeItem(TreeItem item) {
		if (item.getChecked() && !item.getGrayed()) {
			updateChildrenItems(item);
		}
	}

	private void updateChildrenItems(TreeItem parent) {
		Item[] children = getChildren(parent);
		boolean state = parent.getChecked();
		for (Item element : children) {
			TreeItem curr = (TreeItem) element;
			if (curr.getData() != null && ((curr.getChecked() != state) || curr.getGrayed())) {
				curr.setChecked(state);
				curr.setGrayed(false);
				updateChildrenItems(curr);
			}
		}
	}
