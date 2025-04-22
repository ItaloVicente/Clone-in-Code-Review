	public static Test suite() {
		TestSuite suite = new TestSuite(ButtonObservableValueTest.class
				.getName());
		suite.addTestSuite(ButtonObservableValueTest.class);
		suite.addTest(SWTMutableObservableValueContractTest
				.suite(new Delegate()));
