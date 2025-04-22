	public void testInputIfHiddenAndSelectionNotChangesBug485154() throws Throwable {
		PropertySheetPerspectiveFactory3.applyPerspective(activePage);
		PropertySheet propView = (PropertySheet) createTestParts(activePage);
		processUiEvents();

		IViewPart projectExplorer = activePage.showView(IPageLayout.ID_PROJECT_EXPLORER);
		processUiEvents();

		activePage.showView(IPageLayout.ID_PROP_SHEET);
		processUiEvents();

		assertViewsVisibility2(propView, projectExplorer);
		assertEquals(new StructuredSelection(), propView.getShowInContext().getSelection());

		activePage.activate(selectionProviderView);
		processUiEvents();

		createCars();

		int numberToSelect = random.nextInt(NUMBER_OF_CARS - 2);
		ArrayList selection = new ArrayList(numberToSelect);
		while (selection.size() < numberToSelect) {
			int j = random.nextInt(NUMBER_OF_CARS);
			if (!selection.contains(cars[j])) {
				selection.add(cars[j]);
			}
		}
		StructuredSelection structuredSelection = new StructuredSelection(selection);
		selectionProviderView.setSelection(structuredSelection);
		processUiEvents();

		assertEquals(structuredSelection, propView.getShowInContext().getSelection());

		for (int i = 0; i < 10; i++) {
			activePage.activate(projectExplorer);
			processUiEvents();

			assertViewsVisibility1(propView, projectExplorer);

			assertEquals(structuredSelection, propView.getShowInContext().getSelection());

			activePage.showView(IPageLayout.ID_PROP_SHEET);
			processUiEvents();

			assertViewsVisibility2(propView, projectExplorer);

			assertEquals(structuredSelection, propView.getShowInContext().getSelection());
		}
	}

	private void processUiEvents() {
		while (Display.getCurrent().readAndDispatch()) {
