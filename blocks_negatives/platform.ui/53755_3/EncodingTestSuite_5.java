	/**
	 * Returns the suite.  This is required to
	 * use the JUnit Launcher.
	 * @return Test
	 */
	public static Test suite() {
		return new EncodingTestSuite();
	}

	/**
	 * Create the suite.
	 */
	public EncodingTestSuite() {
		super();
		addTest(new TestSuite(EncodingTestCase.class));
	}



