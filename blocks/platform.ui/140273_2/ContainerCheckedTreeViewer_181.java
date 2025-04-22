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
