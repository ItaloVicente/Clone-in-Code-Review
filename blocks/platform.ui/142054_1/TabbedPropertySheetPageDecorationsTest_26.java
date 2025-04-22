	private DecorationTestsView decorationTestsView;

	private TreeNode[] treeNodes;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		assertNotNull(workbenchWindow);
		IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
		assertNotNull(workbenchPage);
