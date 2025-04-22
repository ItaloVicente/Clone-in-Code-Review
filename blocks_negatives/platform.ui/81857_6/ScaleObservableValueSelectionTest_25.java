	public static Test suite() {
		TestSuite suite = new TestSuite(ScaleObservableValueSelectionTest.class
				.toString());
		suite.addTestSuite(ScaleObservableValueSelectionTest.class);
		suite.addTest(SWTMutableObservableValueContractTest
				.suite(new Delegate()));
