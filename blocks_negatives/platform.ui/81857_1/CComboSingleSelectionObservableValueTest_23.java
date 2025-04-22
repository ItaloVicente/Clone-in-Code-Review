	public static Test suite() {
		TestSuite suite = new TestSuite(
				CComboSingleSelectionObservableValueTest.class.getName());
		suite.addTestSuite(CComboSingleSelectionObservableValueTest.class);
		suite.addTest(SWTMutableObservableValueContractTest
				.suite(new Delegate()));
		return suite;
