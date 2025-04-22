	public static Test suite() {
		TestSuite suite = new TestSuite(ScaleObservableValueMaxTest.class
				.toString());
		suite.addTestSuite(ScaleObservableValueMaxTest.class);
		suite.addTest(SWTMutableObservableValueContractTest
				.suite(new Delegate()));
