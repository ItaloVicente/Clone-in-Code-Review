    /**
     * Construct the test suite.
     */
    public ZoomTestSuite() {
        addTest(new TestSuite(ZoomedViewActivateTest.class));
        addTest(new TestSuite(ZoomedEditorCloseTest.class));
        addTest(new TestSuite(ZoomedViewCloseTest.class));
        addTest(new TestSuite(ShowViewTest.class));
        addTest(new TestSuite(OpenEditorTest.class));
    }
