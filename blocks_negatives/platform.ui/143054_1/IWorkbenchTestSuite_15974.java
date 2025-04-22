    /**
     * Construct the test suite.
     */
    public IWorkbenchTestSuite() {
        addTest(new TestSuite(IWorkbenchTest.class));
        addTest(new TestSuite(IWorkbenchWindowTest.class));
    }
