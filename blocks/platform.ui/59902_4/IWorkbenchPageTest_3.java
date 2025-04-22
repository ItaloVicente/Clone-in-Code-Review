		assertNull(fActivePage.findViewReference(MockViewPart.ID2));
		assertNotNull(fActivePage.findViewReference(MockViewPart.ID));

		fActivePage.showView(MockViewPart.ID2);
		assertNotNull(fActivePage.findViewReference(MockViewPart.ID2));
		assertNotNull(fActivePage.findView(MockViewPart.ID2));

		fWin.getWorkbench().showPerspective(SessionPerspective.ID, fWin);
		assertNull(fActivePage.findView(MockViewPart.ID4));
		assertNull(fActivePage.findView(MockViewPart.ID2));
		assertNotNull(fActivePage.findView(SessionView.VIEW_ID));

		assertNull(fActivePage.findViewReference(MockViewPart.ID4));
		assertNull(fActivePage.findViewReference(MockViewPart.ID2));
		assertNotNull(fActivePage.findViewReference(SessionView.VIEW_ID));

		fActivePage.showView(MockViewPart.ID2);
		assertNotNull(fActivePage.findView(MockViewPart.ID2));
		assertNotNull(fActivePage.findViewReference(MockViewPart.ID2));
