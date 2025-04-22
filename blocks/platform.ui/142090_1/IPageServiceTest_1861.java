	public void testAddPageListener() throws Throwable {

		 fWindow.addPageListener(this);

		 pageEventReceived = false;
		 IWorkbenchPage page = fWindow.openPage(EmptyPerspective.PERSP_ID,
		 fWorkspace);
		 page.close();
		 assertTrue(pageEventReceived);

		 fWindow.removePageListener(this);
	}

	public void XXXtestRemovePageListener() throws Throwable {

		fWindow.addPageListener(this);
		fWindow.removePageListener(this);

		pageEventReceived = false;
		IWorkbenchPage page = fWindow.openPage(EmptyPerspective.PERSP_ID, getPageInput());
		page.close();
		assertTrue(!pageEventReceived);
	}

	public void testGetActivePage() throws Throwable {

		 IWorkbenchPage page1 = fWindow.openPage(EmptyPerspective.PERSP_ID,
		 fWorkspace);
		 assertEquals(fWindow.getActivePage(), page1);

		 IWorkbenchPage page2 = fWindow.openPage(EmptyPerspective.PERSP_ID,
		 fWorkspace);
		 assertEquals(fWindow.getActivePage(), page2);

		 fWindow.setActivePage(page1);
		 assertEquals(fWindow.getActivePage(), page1);
		 fWindow.setActivePage(page2);
		 assertEquals(fWindow.getActivePage(), page2);

		 page1.close();
		 page2.close();
	}

	public void testAddPerspectiveListener() throws Throwable {

		 fWindow.addPerspectiveListener(this);

		 perspEventReceived = false;
		 IWorkbenchPage page = fWindow.openPage(IWorkbenchConstants.DEFAULT_LAYOUT_ID,
		 fWorkspace);
		 page.setEditorAreaVisible(false);
		 page.setEditorAreaVisible(true);
		 page.close();
		 assertTrue(perspEventReceived);

		 fWindow.removePerspectiveListener(this);
	}

	public void XXXtestRemovePerspectiveListener() throws Throwable {

		fWindow.addPerspectiveListener(this);
		fWindow.removePerspectiveListener(this);

		perspEventReceived = false;
		IWorkbenchPage page = fWindow.openPage(IDE.RESOURCE_PERSPECTIVE_ID, getPageInput());
		page.setEditorAreaVisible(false);
		page.setEditorAreaVisible(true);
		page.close();
		assertTrue(!perspEventReceived);
	}

	@Override
