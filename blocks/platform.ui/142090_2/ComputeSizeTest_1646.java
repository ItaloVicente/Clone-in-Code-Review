	private TestWidgetFactory widgetFactory;
	private int xIterations = 10;
	private int yIterations = 10;

	public ComputeSizeTest(TestWidgetFactory widgetFactory) {
		super(widgetFactory.getName() + " computeSize");

		this.widgetFactory = widgetFactory;
	}

	@Override
