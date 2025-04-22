    private TestWidgetFactory widgetFactory;
    private int xIterations = 10;
    private int yIterations = 10;

    /**
     * @param testName
     */
    public ComputeSizeTest(TestWidgetFactory widgetFactory) {
        super(widgetFactory.getName() + " computeSize");

        this.widgetFactory = widgetFactory;
    }

    /**
     * Run the test
     */
    @Override
