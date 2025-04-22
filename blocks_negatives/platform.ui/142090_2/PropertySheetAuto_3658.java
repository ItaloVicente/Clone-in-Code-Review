    }

    /**
     * Supply selection events with a random car selection. None of these should go to
     * the properties view because it is hidden.
     * <p>
     * This test invalidated by the fix for
     * </p>
     */
    public void XtestInputIfHiddenBug69953() throws Throwable {
        PropertySheetPerspectiveFactory2.applyPerspective(activePage);
        PropertySheet propView = (PropertySheet) createTestParts(activePage);
        createCars();

        assertFalse("'Property' view should be hidden", activePage.isPartVisible(propView));
        assertTrue("'Selection provider' view should be visible", activePage
                .isPartVisible(selectionProviderView));

        for (int i = 0; i < NUMBER_OF_SELECTIONS; i++) {
            int numberToSelect = random.nextInt(NUMBER_OF_CARS - 2);
