	public static Test suite() {
		TestSuite suite = new TestSuite(TextEditableObservableValueTest.class
				.toString());
		suite.addTestSuite(TextEditableObservableValueTest.class);
		suite.addTest(SWTMutableObservableValueContractTest
				.suite(new Delegate()));
		return suite;
