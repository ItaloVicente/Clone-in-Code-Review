	public static Test suite() {
		TestSuite suite = new TestSuite(ShellObservableValueTest.class
				.toString());
		suite.addTestSuite(ShellObservableValueTest.class);
		suite.addTest(SWTMutableObservableValueContractTest
				.suite(new Delegate()));
