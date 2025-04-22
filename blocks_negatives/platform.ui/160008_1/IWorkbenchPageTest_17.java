	public IWorkbenchPageTest() {
		super(IWorkbenchPageTest.class.getName());
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		fWin = openTestWindow();
