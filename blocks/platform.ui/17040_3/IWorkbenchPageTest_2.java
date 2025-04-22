	public void testFindSecondaryViewReference() throws Throwable {
		fActivePage
				.getWorkbenchWindow()
				.getWorkbench()
				.showPerspective(SessionPerspective.ID,
						fActivePage.getWorkbenchWindow());
		assertNull(fActivePage.findViewReference(MockViewPart.IDMULT, "1"));

		fActivePage.showView(MockViewPart.IDMULT, "1",
				IWorkbenchPage.VIEW_ACTIVATE);
		assertNotNull(fActivePage.findViewReference(MockViewPart.IDMULT, "1"));
	}
	
