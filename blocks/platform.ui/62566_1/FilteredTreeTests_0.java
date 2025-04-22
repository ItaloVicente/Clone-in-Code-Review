
		assertTreeViewerExists();
		assertNumberOfTopLevelItems(NUM_ITEMS);

		dialog.close();
	}

	public void testAddAndRemovePattern() {
		Dialog dialog = createFilteredTreeDialog();

		assertTreeViewerExists();
		assertNumberOfTopLevelItems(NUM_ITEMS);

		applyPattern("0-0-0-0 name-*");
		assertNumberOfTopLevelItems(1);

		applyPattern("");
		assertNumberOfTopLevelItems(NUM_ITEMS);

