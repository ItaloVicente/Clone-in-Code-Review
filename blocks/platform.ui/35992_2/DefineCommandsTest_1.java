	private IEclipseContext workbenchContext;

	@Before
	public void setUp() {
		IEclipseContext globalContext = TestActivator.getDefault().getGlobalContext();
		workbenchContext = globalContext.createChild("workbenchContext");
		ContextInjectionFactory.make(CommandServiceAddon.class, workbenchContext);
	}

	@After
	public void tearDown() {
		workbenchContext.dispose();
	}

	@Test
	public void testCreateCommands() {
