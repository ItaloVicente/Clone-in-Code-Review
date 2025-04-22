
		 fWindow.addPageListener(this);

		 pageEventReceived = false;
		 IWorkbenchPage page = fWindow.openPage(EmptyPerspective.PERSP_ID,
		 fWorkspace);
		 page.close();
		 assertTrue(pageEventReceived);

		 fWindow.removePageListener(this);
