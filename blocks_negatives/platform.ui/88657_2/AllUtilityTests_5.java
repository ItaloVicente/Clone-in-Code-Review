	/*
	 * Returns the entire test suite.
	 */
	public static Test suite() {
		return new AllUtilityTests();
	}

	/*
	 * Constructs a new performance test suite.
	 */
	public AllUtilityTests() {
		addTestSuite(FormImagesTests.class);
		addTestSuite(FormFontsTests.class);
		addTestSuite(FormColorsTests.class);
		addTestSuite(FormToolkitTest.class);
		addTestSuite(ImageHyperlinkTest.class);
	}
