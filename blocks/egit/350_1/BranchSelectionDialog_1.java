
	private void expandTreeElement(boolean expand) {
		TreeItem[] selection = branchTree.getSelection();
		if (selection != null && selection.length > 0) {
			TreeItem item = selection[0];
			item.setExpanded(expand);
		}
	}
