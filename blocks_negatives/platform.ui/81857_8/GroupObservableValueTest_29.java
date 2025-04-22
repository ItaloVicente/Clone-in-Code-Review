	public static Test suite() {
		TestSuite suite = new TestSuite(GroupObservableValueTest.class
				.toString());
		suite.addTestSuite(GroupObservableValueTest.class);
		suite.addTest(SWTMutableObservableValueContractTest
				.suite(new Delegate()));
