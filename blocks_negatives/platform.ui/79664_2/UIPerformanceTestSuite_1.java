    /**
     * Construct the test suite.
     */
    public UIPerformanceTestSuite() {
    	super();
        addTest(new ActivitiesPerformanceSuite());
        addTest(new WorkbenchPerformanceSuite());
        addTest(new ViewPerformanceSuite());
        addTest(new EditorPerformanceSuite());
        addTest(new TestSuite(CommandsPerformanceTest.class));
