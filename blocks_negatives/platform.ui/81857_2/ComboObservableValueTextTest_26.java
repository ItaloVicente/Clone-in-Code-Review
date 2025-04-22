	public static Test suite() {
		TestSuite suite = new TestSuite(ComboObservableValueTextTest.class
				.toString());
		suite.addTestSuite(ComboObservableValueTextTest.class);
		suite.addTest(SWTMutableObservableValueContractTest
				.suite(new Delegate()));
		return suite;
