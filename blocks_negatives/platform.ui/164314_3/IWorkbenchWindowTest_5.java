		 *
		 IWorkbenchPage page = null;
		 try {
		 page = fWin.openPage(EmptyPerspective.PERSP_ID, ResourcesPlugin.getWorkspace());
		 assertNotNull(page);
		 assertEquals(fWin.getActivePage(), page);
		 assertEquals(
		 fWin.getActivePage().getPerspective().getId(),
		 EmptyPerspective.PERSP_ID);
		 } finally {
		 if (page != null)
		 page.close();
		 }

		 try {
		 page = fWin.openPage("*************", ResourcesPlugin.getWorkspace());
		 fail();
		 } catch (WorkbenchException ex) {
		 }

		 page.close();
