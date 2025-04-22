	public static Test suite() {
		TestSuite suite = new TestSuite(AbstractObservableTest.class.getName());
		suite.addTestSuite(AbstractObservableTest.class);
		Delegate delegate = new Delegate();
		suite.addTest(ObservableContractTest.suite(delegate));
		suite.addTest(ObservableStaleContractTest.suite(delegate));
		return suite;
