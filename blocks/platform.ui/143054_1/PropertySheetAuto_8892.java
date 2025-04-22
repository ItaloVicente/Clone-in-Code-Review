		return view;

	}

	public void testInput() throws Throwable {
		PropertySheetPerspectiveFactory.applyPerspective(activePage);
		PropertySheet propView = (PropertySheet) createTestParts(activePage);
		createCars();

		assertTrue("'Property' view should be visible", activePage.isPartVisible(propView));
		assertTrue("'Selection provider' view should be visible", activePage
				.isPartVisible(selectionProviderView));

		for (int i = 0; i < NUMBER_OF_SELECTIONS; i++) {
			int numberToSelect = random.nextInt(NUMBER_OF_CARS - 2);
