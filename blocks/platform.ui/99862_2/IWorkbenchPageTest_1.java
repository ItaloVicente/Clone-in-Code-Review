	public void testGetViewStack() throws PartInitException {
		IViewPart part = fActivePage.showView(MockViewPart.ID);

		IViewPart[] stack = fActivePage.getViewStack(part);
		assertEquals(1, stack.length);

		assertEquals(part, stack[0]);
	}

	public void testGetViewStackWithSecondaryId() throws PartInitException {
		IViewPart part = fActivePage.showView(MockViewPart.ID, "1", IWorkbenchPage.VIEW_CREATE);

		IViewPart[] stack = fActivePage.getViewStack(part);
		assertEquals(1, stack.length);

		assertEquals(part, stack[0]);
	}
