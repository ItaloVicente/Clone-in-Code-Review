        }
    }

    public void testOpenPage1() throws Throwable {
        IWorkbenchWindow win = null;
        try {
            /*
             * Commented out until test case can be updated to match new
             * implementation of single page per window
             *
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
             */
            /*
             * Commented out until Nick has time to update this
             * test case to match new implementation of openPage
             * otherwise this test always fails.
             *
             page1 = fWorkbench.openPage(ResourcesPlugin.getWorkspace());
             assertNotNull(page1);
             page2 = fWorkbench.openPage(ResourcesPlugin.getWorkspace());
             assertNotNull(page2);
             assertTrue("Pages should be not equal", page1 != page2);
             */
        } finally {
            if (win != null) {
