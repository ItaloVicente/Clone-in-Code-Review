	public static Test suite() {
		TestSuite suite = new TestSuite("ConstantValueTest");
		suite.addTestSuite(ConstantObservableValueTest.class);
		suite.addTest(UnchangeableObservableValueContractTest
				.suite(new Delegate()));
		return suite;
