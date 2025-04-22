	public static Test suite() {
		TestSuite suite = new TestSuite(
				SpinnerObservableValueSelectionTest.class.toString());
		suite.addTestSuite(SpinnerObservableValueSelectionTest.class);
		suite.addTest(SWTMutableObservableValueContractTest
				.suite(new Delegate()));
		return suite;
