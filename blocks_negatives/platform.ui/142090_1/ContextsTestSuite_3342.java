    /**
     * Constructs a new instance of <code>ContextsTestSuite</code> with all of
     * the relevent test cases.
     */
    public ContextsTestSuite() {
        addTestSuite(Bug74990Test.class);
        addTestSuite(Bug84763Test.class);
        addTestSuite(ExtensionTestCase.class);
        addTestSuite(PartContextTest.class);
    }
