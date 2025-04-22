	}

	public void XtestInputIfHiddenBug69953() throws Throwable {
		PropertySheetPerspectiveFactory2.applyPerspective(activePage);
		PropertySheet propView = (PropertySheet) createTestParts(activePage);
		createCars();

		assertFalse("'Property' view should be hidden", activePage.isPartVisible(propView));
		assertTrue("'Selection provider' view should be visible", activePage
				.isPartVisible(selectionProviderView));

		for (int i = 0; i < NUMBER_OF_SELECTIONS; i++) {
			int numberToSelect = random.nextInt(NUMBER_OF_CARS - 2);
