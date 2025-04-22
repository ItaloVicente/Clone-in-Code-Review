	public static Test suite() {
		TestSuite suite = new TestSuite(
				ListDetailValueObservableListTest.class.getName());
		suite.addTestSuite(ListDetailValueObservableListTest.class);
		suite.addTest(ObservableListContractTest.suite(new Delegate()));
		return suite;
