
		fWindow.addPerspectiveListener(this);

		perspEventReceived = false;
		IWorkbenchPage page = fWindow.openPage(IDE.RESOURCE_PERSPECTIVE_ID, fWorkspace);
		page.setEditorAreaVisible(false);
		page.setEditorAreaVisible(true);
		page.close();
		assertTrue(perspEventReceived);

		fWindow.removePerspectiveListener(this);
