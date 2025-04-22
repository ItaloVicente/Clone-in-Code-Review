		assertNotNull(fActivePage.findView(MockViewPart.ID4));
		assertNotNull(fActivePage.findViewReference(MockViewPart.ID4));
	}

	public void testFindViewReferenceAfterShowViewCommand() throws Throwable {
		fWin.getWorkbench().showPerspective(ViewPerspective.ID, fWin);
		assertNull(fActivePage.findView(MockViewPart.ID4));
		assertNull(fActivePage.findView(MockViewPart.ID2));
		assertNotNull(fActivePage.findView(MockViewPart.ID));

		assertNull(fActivePage.findViewReference(MockViewPart.ID4));
		assertNull(fActivePage.findViewReference(MockViewPart.ID2));
		assertNotNull(fActivePage.findViewReference(MockViewPart.ID));

		showViewViaCommand(MockViewPart.ID2);
		assertNotNull(fActivePage.findView(MockViewPart.ID2));
		assertNotNull(fActivePage.findViewReference(MockViewPart.ID2));

		fWin.getWorkbench().showPerspective(SessionPerspective.ID, fWin);
		assertNull(fActivePage.findView(MockViewPart.ID2));
		assertNull(fActivePage.findView(MockViewPart.ID4));
		assertNotNull(fActivePage.findView(SessionView.VIEW_ID));

		assertNull(fActivePage.findViewReference(MockViewPart.ID2));
		assertNull(fActivePage.findViewReference(MockViewPart.ID4));
		assertNotNull(fActivePage.findViewReference(SessionView.VIEW_ID));

		showViewViaCommand(MockViewPart.ID2);
		assertNotNull(fActivePage.findView(MockViewPart.ID2));
		assertNotNull(fActivePage.findViewReference(MockViewPart.ID2));

		showViewViaCommand(MockViewPart.ID4);
		assertNotNull(fActivePage.findView(MockViewPart.ID4));
