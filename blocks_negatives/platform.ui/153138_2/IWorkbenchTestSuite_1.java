
	/**
	 * Returns the suite.  This is required to
	 * use the JUnit Launcher.
	 */
	public static Test suite() {
		return new IWorkbenchTestSuite();
	}

	/**
	 * Construct the test suite.
	 */
	public IWorkbenchTestSuite() {
		addTest(new TestSuite(IWorkbenchTest.class));
		addTest(new TestSuite(IWorkbenchWindowTest.class));
	}
