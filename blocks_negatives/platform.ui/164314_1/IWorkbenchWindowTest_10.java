		 *
		 IWorkbenchPage page = null;
		 try {
		 page = fWin.openPage(ResourcesPlugin.getWorkspace());
		 assertNotNull(page);
		 assertEquals(fWin.getActivePage(), page);
		 } finally {
		 if (page != null)
		 page.close();
		 }
