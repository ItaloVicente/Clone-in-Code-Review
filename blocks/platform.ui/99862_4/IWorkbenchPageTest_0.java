	public void testGetViewStackWithoutSecondaryId() throws PartInitException {
		IViewPart part = fActivePage.showView(MockViewPart.ID);
		assertEquals(MockViewPart.ID, part.getViewSite().getId());
		assertNull(part.getViewSite().getSecondaryId());

		IViewPart[] stack = fActivePage.getViewStack(part);
		assertEquals(1, stack.length);

		assertEquals(part, stack[0]);
	}

	public void testGetViewStackWithSecondaryId() throws PartInitException {
		IViewPart part = fActivePage.showView(MockViewPart.ID, "1", IWorkbenchPage.VIEW_CREATE);
		assertEquals(MockViewPart.ID, part.getViewSite().getId());
		assertEquals("1", part.getViewSite().getSecondaryId());

		IViewPart[] stack = fActivePage.getViewStack(part);
		assertEquals(1, stack.length);

		assertEquals(part, stack[0]);
	}
