		IPropertySheetEntry entry = (IPropertySheetEntry) treeItem.getData();
		entry.applyEditorValue();
	}

	private void createChildren(Widget widget) {
		TreeItem[] childItems = getChildItems(widget);

		if (childItems.length > 0) {
			Object data = childItems[0].getData();
			if (data != null) {
