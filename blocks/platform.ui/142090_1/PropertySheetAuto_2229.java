	public void testInputIfHidden2Bug69953() throws Throwable {
		PropertySheetPerspectiveFactory3.applyPerspective(activePage);
		PropertySheet propView = (PropertySheet) createTestParts(activePage);
		createCars();
		for (int i = 0; i < 10; i++) {
			IViewPart projectExplorer = activePage.showView(IPageLayout.ID_PROJECT_EXPLORER);
			assertViewsVisibility1(propView, projectExplorer);

			activePage.activate(selectionProviderView);
