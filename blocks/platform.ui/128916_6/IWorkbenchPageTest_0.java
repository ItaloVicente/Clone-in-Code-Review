	public void testRelativeViewVisibility() {
		processEvents();

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

		IViewPart view;

		view = activePage.findView(MockViewPart.ID);
		assertNotNull("View should be there", view);
		assertTrue("View should be visible", fActivePage.isPartVisible(view));

		view = activePage.findView("org.eclipse.ui.tests.api.MockViewPartVisibleByDefault");
		assertTrue("Relative 'visible' view should be visible even if the relative does not exist", //$NON-NLS-1$
				fActivePage.isPartVisible(view));

		view = activePage.findView("org.eclipse.ui.tests.api.MockViewPartInvisibleByDefault");
		assertNull("Relative 'invisible' part should not be visible if the relative does not exist", view);
	}

