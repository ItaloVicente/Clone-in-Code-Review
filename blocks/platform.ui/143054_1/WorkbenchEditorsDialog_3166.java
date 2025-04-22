		}
		TableItem result[] = new TableItem[cleanItems.size()];
		cleanItems.toArray(result);
		return result;
	}

	private TableItem[] invertedSelection(TableItem allItems[], TableItem selectedItems[]) {
		if (allItems.length == 0) {
