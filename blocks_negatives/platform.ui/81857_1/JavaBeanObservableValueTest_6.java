	public static Test suite() {
		TestSuite suite = new TestSuite(JavaBeanObservableValueTest.class
				.getName());
		suite.addTestSuite(JavaBeanObservableValueTest.class);
		suite.addTest(MutableObservableValueContractTest.suite(new Delegate()));
		return suite;
