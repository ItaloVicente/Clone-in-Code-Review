		}
		return null;
	}

	private TreeItem findItem(IPropertySheetEntry entry, TreeItem item) {
		Object mapItem = entryToItemMap.get(entry);
		if (mapItem != null && mapItem instanceof TreeItem)
			return (TreeItem) mapItem;

		if (entry == item.getData()) {
