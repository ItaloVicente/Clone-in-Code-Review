	public static Test suite() {
		TestSuite suite = new TestSuite(ViewerInputObservableValueTest.class
				.getName());
		suite.addTestSuite(ViewerInputObservableValueTest.class);
		suite.addTest(MutableObservableValueContractTest.suite(new Delegate()));
		return suite;
