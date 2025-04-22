	@Before
	public void setUp() throws Exception {
		appContext = E4Application.createDefaultContext();
		appContext.set(IWorkbench.PRESENTATION_URI_ARG,
				PartRenderingEngine.engineURI);
		ems = appContext.get(EModelService.class);
	}
