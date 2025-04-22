	public static Test suite() {
		TestSuite suite = new TestSuite(LabelObservableValueTest.class
				.toString());
		suite.addTestSuite(LabelObservableValueTest.class);
		suite.addTest(SWTMutableObservableValueContractTest
				.suite(new Delegate()));
		return suite;
