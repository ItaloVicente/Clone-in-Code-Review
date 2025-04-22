	private MToolBar toolBar;
	private MTrimmedWindow window;

	@Before
	public void setUp() throws Exception {
		window = ems.createModelElement(MTrimmedWindow.class);
		application.getChildren().add(window);

		MTrimBar trimBar = ems.createModelElement(MTrimBar.class);
		window.getTrimBars().add(trimBar);

		toolBar = ems.createModelElement(MToolBar.class);
		trimBar.getChildren().add(toolBar);
	}

