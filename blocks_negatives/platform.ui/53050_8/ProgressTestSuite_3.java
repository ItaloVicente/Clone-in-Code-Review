	/**
	 * Returns the suite. This is required to use the JUnit Launcher.
	 */
	public static final Test suite() {
		return new ProgressTestSuite();
	}

	public ProgressTestSuite() {
		addTest(new TestSuite(ProgressContantsTest.class));
		addTest(new TestSuite(ProgressViewTests.class));
		addTest(new TestSuite(JobInfoTest.class));
		addTest(new TestSuite(JobInfoTestOrdering.class));
		addTest(new TestSuite(ProgressAnimationItemTest.class));
	}
