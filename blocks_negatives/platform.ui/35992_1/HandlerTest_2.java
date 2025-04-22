	private IEclipseContext workbenchContext;

	@Override
	protected void setUp() throws Exception {
		IEclipseContext globalContext = TestActivator.getDefault().getGlobalContext();
		workbenchContext = globalContext.createChild("workbenchContext");
		ContextInjectionFactory.make(CommandServiceAddon.class, workbenchContext);
		defineCommands(workbenchContext);
	}

	@Override
	protected void tearDown() throws Exception {
		workbenchContext.dispose();
	}

