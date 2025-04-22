	public IWorkingSetTest() {
		super(IWorkingSetTest.class.getSimpleName());
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		IWorkingSetManager workingSetManager = fWorkbench
				.getWorkingSetManager();
