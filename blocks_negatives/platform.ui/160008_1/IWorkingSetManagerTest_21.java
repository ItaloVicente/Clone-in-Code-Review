	public IWorkingSetManagerTest() {
		super(IWorkingSetManagerTest.class.getSimpleName());
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		fWorkingSetManager = fWorkbench.getWorkingSetManager();
