	/**
	 * Constructor for <code>HandlerActivationTest</code>.
	 */
	public HandlerActivationTest() {
		super(HandlerActivationTest.class.getSimpleName());
		services = PlatformUI.getWorkbench();
		contextService = services
				.getService(IContextService.class);
		commandService = services
				.getService(ICommandService.class);
		handlerService = services
				.getService(IHandlerService.class);
	}

