	public static Test suite() {
		TestSuite suite = new TestSuite(
				CComboObservableValueSelectionTest.class.getName());
		suite.addTestSuite(CComboObservableValueSelectionTest.class);
		suite.addTest(SWTMutableObservableValueContractTest
				.suite(new Delegate()));
		return suite;
