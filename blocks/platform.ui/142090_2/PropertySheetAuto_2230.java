		assertFalse("'Property' view should be hidden", activePage.isPartVisible(propView));
		assertTrue("'Project Explorer' view should be visible", activePage
				.isPartVisible(projectExplorer));
		assertTrue("'Selection provider' view should be visible", activePage
				.isPartVisible(selectionProviderView));
	}

	private void assertViewsVisibility2(PropertySheet propView, IViewPart projectExplorer) {
