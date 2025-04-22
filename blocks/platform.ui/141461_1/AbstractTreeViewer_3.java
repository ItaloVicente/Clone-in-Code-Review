		elementloop: for (Object element : elements) {
		    indexInItems = insertionPosition(items, comparator,
			    indexInItems, element, parentPath);
		    if (indexInItems == items.length) {
			createTreeItem(widget, element, -1);
			newItems++;
		    } else {

			int insertionIndexInItems = indexInItems;
			while( insertionIndexInItems < items.length
				&& internalCompare(comparator, parentPath, element,
					items[insertionIndexInItems].getData()) == 0) {
			    if (items[insertionIndexInItems].getData().equals(element)) {
				internalRefresh(element);
				continue elementloop;
			    }
			    insertionIndexInItems++;
			}
			if (insertionIndexInItems == items.length) {
			    createTreeItem(widget, element, -1);
			    newItems++;
