	public Bug540297WorkbenchPageFindViewTest() {
		super(Bug540297WorkbenchPageFindViewTest.class.getSimpleName());
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();

		firstWindow = fWorkbench.getActiveWorkbenchWindow();
		secondWindow = openTestWindow();
