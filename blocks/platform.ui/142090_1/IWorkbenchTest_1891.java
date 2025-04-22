		}
	}

	public void testOpenPage2() throws Throwable {
		IWorkbenchWindow win = null;
		try {
			 win = fWorkbench.openWorkbenchWindow(ResourcesPlugin.getWorkspace());
			 assertNotNull(win);

			 IPreferenceStore store = WorkbenchPlugin.getDefault().getPreferenceStore();
			 store.setValue(IPreferenceConstants.REUSE_PERSPECTIVES,
			 true);

			 page1 = fWorkbench.openPage(EmptyPerspective.PERSP_ID,
			 ResourcesPlugin.getWorkspace(), 0);
			 assertNotNull(page1);
			 page2 = fWorkbench.openPage(IWorkbenchConstants.DEFAULT_LAYOUT_ID,
			 ResourcesPlugin.getWorkspace(), 0);
			 assertNotNull(page2);
			 assertEquals("Pages should be equal", page1, page2);

			 store.setValue(IPreferenceConstants.REUSE_PERSPECTIVES,
			 false);

			 page1 = fWorkbench.openPage(EmptyPerspective.PERSP_ID,
			 ResourcesPlugin.getWorkspace(), 0);
			 assertNotNull(page1);
			 page2 = fWorkbench.openPage(IWorkbenchConstants.DEFAULT_LAYOUT_ID,
			 ResourcesPlugin.getWorkspace(), 0);
			 assertTrue("Pages should be not equal", page1 != page2);
		} finally {
			if (win != null) {
