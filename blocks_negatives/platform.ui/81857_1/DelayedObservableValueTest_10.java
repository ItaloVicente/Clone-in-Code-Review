	public static Test suite() {
		TestSuite suite = new TestSuite(DelayedObservableValueTest.class
				.getName());
		suite.addTestSuite(DelayedObservableValueTest.class);
		suite.addTest(MutableObservableValueContractTest
				.suite(new Delegate()));
		return suite;
