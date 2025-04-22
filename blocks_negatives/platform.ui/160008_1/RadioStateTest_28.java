	public RadioStateTest(String testName) {
		super(testName);
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		commandService = fWorkbench
				.getService(ICommandService.class);
		handlerService = fWorkbench
				.getService(IHandlerService.class);
