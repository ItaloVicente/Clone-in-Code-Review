	private IWorkbench fWorkbench;

	@Before
	public void doSetUp() throws Exception {
		fWorkbench = PlatformUI.getWorkbench();
		commandService = fWorkbench.getService(ICommandService.class);
		handlerService = fWorkbench.getService(IHandlerService.class);
		contextService = fWorkbench.getService(IContextService.class);
		evalService = fWorkbench.getService(IEvaluationService.class);
