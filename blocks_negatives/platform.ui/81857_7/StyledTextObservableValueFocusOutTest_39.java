	public static Test suite() {
		TestSuite suite = new TestSuite(
				StyledTextObservableValueFocusOutTest.class.toString());
		suite.addTestSuite(StyledTextObservableValueFocusOutTest.class);
		suite.addTest(SWTMutableObservableValueContractTest
				.suite(new Delegate()));
