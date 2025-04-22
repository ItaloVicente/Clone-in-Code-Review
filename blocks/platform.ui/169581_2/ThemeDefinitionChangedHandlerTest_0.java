	private EModelService modelService;

	@Before
	public void setup() {
		modelService = new ModelServiceImpl(EclipseContextFactory.create());
	}

