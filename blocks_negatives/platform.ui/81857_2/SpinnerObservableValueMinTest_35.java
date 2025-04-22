	public static Test suite() {
		TestSuite suite = new TestSuite(SpinnerObservableValueMinTest.class
				.toString());
		suite.addTestSuite(SpinnerObservableValueMinTest.class);
		suite.addTest(SWTMutableObservableValueContractTest
				.suite(new Delegate()));
		return suite;
