	private void updateParentItems(TreeItem item) {
		if (item != null) {
			Item[] children = getChildren(item);
			boolean containsChecked = false;
			boolean containsUnchecked = false;
			for (Item element : children) {
				TreeItem curr = (TreeItem) element;
				boolean currChecked = curr.getChecked();
