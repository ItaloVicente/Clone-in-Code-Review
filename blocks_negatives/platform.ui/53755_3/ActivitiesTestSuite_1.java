    /**
     * Construct the test suite.
     */
    public ActivitiesTestSuite() {
      	addTest(new TestSuite(ImagesTest.class));
		addTest(new TestSuite(UtilTest.class));
        addTest(new TestSuite(StaticTest.class));
        addTest(new TestSuite(DynamicTest.class));
        addTest(new TestSuite(PersistanceTest.class));
        addTest(new TestSuite(ActivityPreferenceTest.class));
        addTest(new TestSuite(MenusTest.class));
        addTest(new TestSuite(PatternUtilTest.class));
    }
