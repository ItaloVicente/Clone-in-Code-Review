
	/**
	 * Returns the suite.  This is required to
	 * use the JUnit Launcher.
	 */
	public static Test suite() {
		return new OpenCloseTestSuite();
	}

	/**
	 * Construct the test suite.
	 */
	public OpenCloseTestSuite() {
		addTest(new TestSuite(OpenCloseTest.class));

	}
