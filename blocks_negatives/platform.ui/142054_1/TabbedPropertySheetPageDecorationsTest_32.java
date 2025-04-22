    private DecorationTestsView decorationTestsView;

    private TreeNode[] treeNodes;

    @Override
	protected void setUp()
        throws Exception {
        super.setUp();

        /**
         * Close the existing perspectives.
         */
        IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench()
            .getActiveWorkbenchWindow();
        assertNotNull(workbenchWindow);
        IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
        assertNotNull(workbenchPage);
