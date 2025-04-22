	public static Test suite() {
		TestSuite suite = new TestSuite(CComboObservableValueTextTest.class
				.getName());
		suite.addTestSuite(CComboObservableValueTextTest.class);
		suite.addTest(SWTMutableObservableValueContractTest
				.suite(new Delegate()));
