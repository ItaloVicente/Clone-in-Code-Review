
		IWorkbenchPage page1 = fWindow.openPage(EmptyPerspective.PERSP_ID, fWorkspace);
		assertEquals(fWindow.getActivePage(), page1);

		IWorkbenchPage page2 = fWindow.openPage(EmptyPerspective.PERSP_ID, fWorkspace);
		assertEquals(fWindow.getActivePage(), page2);

		fWindow.setActivePage(page1);
		assertEquals(fWindow.getActivePage(), page1);
		fWindow.setActivePage(page2);
		assertEquals(fWindow.getActivePage(), page2);

		page1.close();
		page2.close();
