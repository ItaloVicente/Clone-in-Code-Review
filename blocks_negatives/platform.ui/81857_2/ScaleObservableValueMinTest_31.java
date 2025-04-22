	public static Test suite() {
		TestSuite suite = new TestSuite(ScaleObservableValueMinTest.class
				.toString());
		suite.addTestSuite(ScaleObservableValueMinTest.class);
		suite.addTest(SWTMutableObservableValueContractTest
				.suite(new Delegate()));
		return suite;
