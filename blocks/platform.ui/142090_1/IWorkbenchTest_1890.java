		}
	}

	public void testOpenPage1() throws Throwable {
		IWorkbenchWindow win = null;
		try {
			 win = fWorkbench.openWorkbenchWindow(ResourcesPlugin.getWorkspace());
			 assertNotNull(win);

			 IPreferenceStore store = WorkbenchPlugin.getDefault().getPreferenceStore();
			 store.setValue(IPreferenceConstants.REUSE_PERSPECTIVES,
			 true);

			 page1 = fWorkbench.openPage(ResourcesPlugin.getWorkspace());
			 assertNotNull(page1);
			 page2 = fWorkbench.openPage(ResourcesPlugin.getWorkspace());
			 assertNotNull(page2);
			 assertEquals("Pages should be equal", page1, page2);

			 store.setValue(IPreferenceConstants.REUSE_PERSPECTIVES,
			 false);
			 page1 = fWorkbench.openPage(ResourcesPlugin.getWorkspace());
			 assertNotNull(page1);
			 page2 = fWorkbench.openPage(ResourcesPlugin.getWorkspace());
			 assertNotNull(page2);
			 assertTrue("Pages should be not equal", page1 != page2);
		} finally {
			if (win != null) {
