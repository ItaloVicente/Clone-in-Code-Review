	public static Test suite() {
		TestSuite suite = new TestSuite(TextObservableValueFocusOutTest.class
				.toString());
		suite.addTestSuite(TextObservableValueFocusOutTest.class);
		suite.addTest(SWTMutableObservableValueContractTest
				.suite(new Delegate()));
