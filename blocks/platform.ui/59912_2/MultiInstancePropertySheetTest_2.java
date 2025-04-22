
	public void testPageDispose() throws Exception {
		activePage.closeAllPerspectives(false, false);

		IPerspectiveDescriptor desc = activePage.getWorkbenchWindow().getWorkbench().getPerspectiveRegistry()
				.findPerspectiveWithId(IDE.RESOURCE_PERSPECTIVE_ID);
		activePage.setPerspective(desc);

		IViewReference[] viewReferences = activePage.getViewReferences();
		for (IViewReference viewReference : viewReferences) {
			if (IPageLayout.ID_PROP_SHEET.equals(viewReference.getId())) {
				activePage.hideView(viewReference);
			}
		}

		processUiEvents();
		testPropertySheetPage.dispose();

		propertySheet = (PropertySheet) activePage.showView(IPageLayout.ID_PROP_SHEET);
	}

	public void testInitialSelectionWithNormalProperties() throws Exception {
		testBug425525(IPageLayout.ID_RES_NAV, true);
	}

	public void testInitialSelectionWithTabbedProperties() throws Exception {
		testBug425525(IPageLayout.ID_PROJECT_EXPLORER, false);
	}

	private void testBug425525(String viewId, boolean standardPage) throws Exception {
		Platform.getAdapterManager().unregisterAdapters(testPropertySheetPage, PropertySheet.class);

		activePage.closeAllPerspectives(false, false);

		IPerspectiveDescriptor desc = activePage.getWorkbenchWindow().getWorkbench().getPerspectiveRegistry()
				.findPerspectiveWithId(IDE.RESOURCE_PERSPECTIVE_ID);
		activePage.setPerspective(desc);

		IViewReference[] viewReferences = activePage.getViewReferences();
		for (IViewReference viewReference : viewReferences) {
			if (IPageLayout.ID_PROP_SHEET.equals(viewReference.getId())) {
				activePage.hideView(viewReference);
			}
		}

		processUiEvents();
		testPropertySheetPage.dispose();

		project = FileUtil.createProject("projectToSelect");

		ISelection selection = new StructuredSelection(project);

		IViewPart contributingView = activePage.showView(viewId);
		contributingView.getSite().getSelectionProvider().setSelection(selection);

		processUiEvents();

		propertySheet = (PropertySheet) activePage.showView(IPageLayout.ID_PROP_SHEET);

		IPage currentPage = propertySheet.getCurrentPage();
		if (standardPage) {
			if (currentPage instanceof PropertySheetPage) {
				PropertySheetPage psp = (PropertySheetPage) currentPage;
				Field root = PropertySheetPage.class.getDeclaredField("rootEntry");
				root.setAccessible(true);
				PropertySheetEntry pse = (PropertySheetEntry) root.get(psp);
				IPropertySheetEntry[] entries = pse.getChildEntries();
				assertTrue("The 'Properties' view should be showing the content of the contributing view ("
						+ contributingView.getTitle() + "), but shows nothing", entries.length > 0);
			} else {
				assertTrue(
						"The 'Properties' view should be showing the content of the contributing view ("
								+ contributingView.getTitle() + ") in a regular property page",
						currentPage instanceof PropertySheetPage);
			}
		} else {
			assertFalse(
					"The 'Properties' view should be showing the content of the contributing view ("
							+ contributingView.getTitle() + ") in a non-standard customiezd page",
					currentPage instanceof PropertySheetPage);
		}

	}

	private void processUiEvents() {
		while (fWorkbench.getDisplay().readAndDispatch()) {
			;
		}
	}

