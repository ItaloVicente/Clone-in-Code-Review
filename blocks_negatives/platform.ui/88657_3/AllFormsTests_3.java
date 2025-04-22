	/*
	 * Constructs a new test suite.
	 */
	public AllFormsTests() {
		addTest(AllLayoutTests.suite());
		addTest(AllUtilityTests.suite());
		addTest(AllWidgetsTests.suite());
	}
