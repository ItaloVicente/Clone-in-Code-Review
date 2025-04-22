    /**
     * Supply selection events with a random car selection after properties view
     * is hidden by the another view in the same stack but the original
     * selection source view is still visible. All of these selections should go
     * to the properties view even if it is hidden. After properties view became
     * visible again, it should show car selection from the (still visible)
     * original source view.
     */
    public void testInputIfHidden2Bug69953() throws Throwable {
        PropertySheetPerspectiveFactory3.applyPerspective(activePage);
        PropertySheet propView = (PropertySheet) createTestParts(activePage);
        createCars();
        for (int i = 0; i < 10; i++) {
            IViewPart projectExplorer = activePage.showView(IPageLayout.ID_PROJECT_EXPLORER);
            assertViewsVisibility1(propView, projectExplorer);

            activePage.activate(selectionProviderView);
