	public static Test suite() {
		TestSuite suite = new TestSuite(MapEntryObservableValueTest.class.getName());
		suite.addTestSuite(MapEntryObservableValueTest.class);
		suite.addTest(MutableObservableValueContractTest.suite(new Delegate()));
		suite.addTest(ObservableStaleContractTest.suite(new Delegate()));
		return suite;
