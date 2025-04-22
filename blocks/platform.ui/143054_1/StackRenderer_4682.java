	private void addItemToSet(List<CTabItem> itemsToSet, MPart parentParent) {
		CTabItem item = findItemForPart(parentParent);
		if (item != null) {
			itemsToSet.add(item);
		}
	}

