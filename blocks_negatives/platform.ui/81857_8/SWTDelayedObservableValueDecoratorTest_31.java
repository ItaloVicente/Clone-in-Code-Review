	public static Test suite() {
		TestSuite suite = new TestSuite(
				SWTDelayedObservableValueDecoratorTest.class.getName());
		suite.addTestSuite(SWTDelayedObservableValueDecoratorTest.class);
		suite.addTest(SWTMutableObservableValueContractTest
				.suite(new Delegate()));
