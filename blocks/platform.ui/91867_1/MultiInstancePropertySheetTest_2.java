	public void testNewPropertySheet() throws Exception {
		assertEquals(1, countPropertySheetViews());
		Platform.addLogListener(logListener);
		executeNewPropertySheetHandler();
		assertEquals(2, countPropertySheetViews());
		if (e != null) {
			throw e;
		}
	}

	public void testNewPropertySheetNoSelection() throws Exception {
		activePage.hideView(selectionProviderView);
		propertySheet = (PropertySheet) activePage.showView(IPageLayout.ID_PROP_SHEET);
		assertEquals(1, countPropertySheetViews());
		PropertyShowInContext context = (PropertyShowInContext) propertySheet.getShowInContext();
		assertNull(context.getPart());
		Platform.addLogListener(logListener);
