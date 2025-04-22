
	public static TestSuite suite() {
		TestSuite ts = new TestSuite();
		ts.addTest(new ToggleStateTest("testDefaultValues"));
		ts.addTest(new ToggleStateTest("testExceptionThrown"));
		ts.addTest(new ToggleStateTest("testMultipleContributions"));
		return ts;
	}

	public ToggleStateTest(String testName) {
		super(testName);
