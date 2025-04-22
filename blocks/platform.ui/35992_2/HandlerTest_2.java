	@Before
	public void setUp() {
		IEclipseContext globalContext = TestActivator.getDefault().getGlobalContext();
		workbenchContext = globalContext.createChild("workbenchContext");
		ContextInjectionFactory.make(CommandServiceAddon.class, workbenchContext);
		defineCommands(workbenchContext);
	}
