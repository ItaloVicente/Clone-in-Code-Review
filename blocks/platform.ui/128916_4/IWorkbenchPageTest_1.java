	public void testRelativeViewVisibility() {
		fActivePage.closeAllPerspectives(true, false);
		IPerspectiveRegistry reg = fWorkbench.getPerspectiveRegistry();
		IPerspectiveDescriptor testPerspective = reg.findPerspectiveWithId(PerspectiveViewsBug538199.ID);
		fActivePage.setPerspective(testPerspective);
		try {
			fWin.getWorkbench().showPerspective(PerspectiveViewsBug538199.ID, fWin);
		} catch (WorkbenchException e) {
			fail("Unexpected WorkbenchException: " + e);
		}
		processEvents();
		IWorkbenchPage activePage = fWin.getActivePage();
		IViewPart dummyView2 = activePage.findView(MockViewPart.IDMULT);
		assertFalse("Relative view should not be visible", fActivePage.isPartVisible(dummyView2)); //$NON-NLS-1$
		IViewPart dummyView = activePage.findView(MockViewPart.ID);
		assertFalse("Relative view should not be visible", fActivePage.isPartVisible(dummyView)); //$NON-NLS-1$
	}

