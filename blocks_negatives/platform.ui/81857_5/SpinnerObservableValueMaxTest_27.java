	public static Test suite() {
		TestSuite suite = new TestSuite(SpinnerObservableValueMaxTest.class
				.toString());
		suite.addTestSuite(SpinnerObservableValueMaxTest.class);
		suite.addTest(SWTMutableObservableValueContractTest
				.suite(new Delegate()));
