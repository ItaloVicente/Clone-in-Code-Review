	public static Test suite() {
		TestSuite suite = new TestSuite(
				TableSingleSelectionObservableValueTest.class.toString());
		suite.addTestSuite(TableSingleSelectionObservableValueTest.class);
		suite.addTest(SWTMutableObservableValueContractTest
				.suite(new Delegate()));
