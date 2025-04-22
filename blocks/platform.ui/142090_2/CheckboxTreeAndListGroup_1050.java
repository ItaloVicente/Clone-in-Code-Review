				}
			});
		}
	}

	protected void populateListViewer(final Object treeElement) {
		listViewer.setInput(treeElement);
		List listItemsToCheck = (List) checkedStateStore.get(treeElement);

		if (listItemsToCheck != null) {
			Iterator listItemsEnum = listItemsToCheck.iterator();
			while (listItemsEnum.hasNext()) {
