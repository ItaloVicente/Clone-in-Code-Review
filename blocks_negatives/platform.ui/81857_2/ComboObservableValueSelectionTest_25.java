	public static Test suite() {
		TestSuite suite = new TestSuite(ComboObservableValueSelectionTest.class
				.toString());
		suite.addTestSuite(ComboObservableValueSelectionTest.class);
		suite.addTest(SWTMutableObservableValueContractTest
				.suite(new Delegate()));
		return suite;
