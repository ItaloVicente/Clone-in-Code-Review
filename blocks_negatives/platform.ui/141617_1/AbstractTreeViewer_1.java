		elementloop: for (int i = 0; i < elements.length; i++) {
			Object element = elements[i];
			indexInItems = insertionPosition(items, comparator,
					indexInItems, element, parentPath);
			if (indexInItems == items.length) {
				createTreeItem(widget, element, -1);
				newItems++;
