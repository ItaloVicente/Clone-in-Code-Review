	@Before
	public void setUp() throws Exception {
		appContext = E4Application.createDefaultContext();
		ContextInjectionFactory.make(CommandServiceAddon.class, appContext);
		appContext.set(IWorkbench.PRESENTATION_URI_ARG, PartRenderingEngine.engineURI);
		appContext.set(UISynchronize.class, new UISynchronize() {
			@Override
			public void syncExec(Runnable runnable) {
				runnable.run();
			}
