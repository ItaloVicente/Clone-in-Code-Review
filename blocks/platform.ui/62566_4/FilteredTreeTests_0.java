
		Assert.isNotNull(fTreeViewer, "Filtered tree is null");
		assertNumberOfTopLevelItems(NUM_ITEMS);

		dialog.close();
	}

	public void testAddAndRemovePattern() {
		Dialog dialog = createFilteredTreeDialog();

