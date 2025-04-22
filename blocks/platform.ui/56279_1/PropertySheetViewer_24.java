		TreeItem[] childItems = getChildItems(widget);

		TreeItem item = null;
		if (widget instanceof TreeItem) {
			item = (TreeItem) widget;
		}
		if (item != null && !item.getExpanded()) {
			for (int i = 0; i < childItems.length; i++) {
				if (childItems[i].getData() != null) {
					removeItem(childItems[i]);
				}
			}

			if (category != null || entry.hasChildEntries()) {
				childItems = getChildItems(widget);
				if (childItems.length == 0) {
					new TreeItem(item, SWT.NULL);
				}
			}
			return;
		}

		if (node == rootEntry && isShowingCategories) {
