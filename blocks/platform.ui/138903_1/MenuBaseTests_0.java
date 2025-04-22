		manager.update(true);
		menuItems = manager.getMenu().getItems();

		assertTrue("manager.update(true): Bad count", menuItems.length == expectedMenuItemLabels.length);

		diffIndex = checkMenuItemLabels(menuItems, expectedMenuItemLabels);
		assertTrue("manager.update(true): Index mismatch at index " + diffIndex , diffIndex == ALL_OK);

		contextMenu.notifyListeners(SWT.Hide, new Event());
		contextMenu.notifyListeners(SWT.Show, new Event());

		menuItems = manager.getMenu().getItems();

		assertTrue("manager.update(true): Bad count", menuItems.length == expectedMenuItemLabels.length);

		diffIndex = checkMenuItemLabels(menuItems, expectedMenuItemLabels);
		assertTrue("manager.update(true): Index mismatch at index " + diffIndex, diffIndex == ALL_OK);
