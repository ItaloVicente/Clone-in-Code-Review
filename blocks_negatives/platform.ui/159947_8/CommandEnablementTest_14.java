
	public CommandEnablementTest() {
		super(CommandEnablementTest.class.getSimpleName());
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		commandService = fWorkbench
				.getService(ICommandService.class);
		handlerService = fWorkbench
				.getService(IHandlerService.class);
		contextService = fWorkbench
				.getService(IContextService.class);
		evalService = fWorkbench
				.getService(IEvaluationService.class);
