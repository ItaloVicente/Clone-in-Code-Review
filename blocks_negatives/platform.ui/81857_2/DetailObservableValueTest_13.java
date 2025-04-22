	public static Test suite() {
		TestSuite suite = new TestSuite(DetailObservableValueTest.class
				.getName());
		suite.addTestSuite(DetailObservableValueTest.class);
		suite.addTest(MutableObservableValueContractTest.suite(new Delegate()));
		return suite;
