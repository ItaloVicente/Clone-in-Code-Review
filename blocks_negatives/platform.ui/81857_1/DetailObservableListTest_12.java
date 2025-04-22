	public static Test suite() {
		TestSuite suite = new TestSuite(DetailObservableListTest.class
				.getName());
		suite.addTestSuite(DetailObservableListTest.class);
		suite.addTest(MutableObservableListContractTest.suite(new Delegate()));
		return suite;
