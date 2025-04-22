		 *

		 fWindow.addPerspectiveListener(this);

		 perspEventReceived = false;
		 IWorkbenchPage page = fWindow.openPage(IWorkbenchConstants.DEFAULT_LAYOUT_ID,
		 fWorkspace);
		 page.setEditorAreaVisible(false);
		 page.setEditorAreaVisible(true);
		 page.close();
		 assertTrue(perspEventReceived);

		 fWindow.removePerspectiveListener(this);
