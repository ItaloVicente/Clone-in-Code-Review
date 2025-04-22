	public void testInputIfHiddenByMaximizeBug509405() throws Throwable {
		PropertySheetPerspectiveFactory3.applyPerspective(activePage);
		IViewPart projectExplorer = activePage.showView(IPageLayout.ID_PROJECT_EXPLORER);
		PropertySheet propView = (PropertySheet) createTestParts(activePage);
		createCars();
		for (int i = 0; i < 10; i++) {
			assertViewsVisibility2(propView, projectExplorer);

			activePage.activate(selectionProviderView);
			processUiEvents();
			activePage.toggleZoom(activePage.getReference(selectionProviderView));
			processUiEvents();

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

			activePage.toggleZoom(activePage.getReference(selectionProviderView));
			processUiEvents();
			assertViewsVisibility2(propView, projectExplorer);

			assertEquals(structuredSelection, propView.getShowInContext().getSelection());

			PropertySheetPage currentPage = (PropertySheetPage) propView.getCurrentPage();
			PropertySheetEntry propEntry = (PropertySheetEntry) currentPage.getControl().getData();
			assertArrayEquals(structuredSelection.toArray(), propEntry.getValues());
		}
	}

