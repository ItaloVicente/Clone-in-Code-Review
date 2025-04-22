	@Before
	public void doSetUp() throws Exception {
		services = PlatformUI.getWorkbench();
		contextService = services.getService(IContextService.class);
		commandService = services.getService(ICommandService.class);
		handlerService = services.getService(IHandlerService.class);
