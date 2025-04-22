	public static Test suite() {
		TestSuite suite = new TestSuite(CLabelObservableValueTest.class
				.getName());
		suite.addTestSuite(CLabelObservableValueTest.class);
		suite.addTest(SWTMutableObservableValueContractTest
				.suite(new Delegate()));
		return suite;
