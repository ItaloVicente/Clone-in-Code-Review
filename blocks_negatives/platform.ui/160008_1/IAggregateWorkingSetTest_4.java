	public IAggregateWorkingSetTest() {
		super(IAggregateWorkingSetTest.class.getSimpleName());
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		IWorkingSetManager workingSetManager = fWorkbench
		.getWorkingSetManager();
