	public static Test suite() {
		TestSuite suite = new TestSuite(JavaBeanObservableSetTest.class
				.getName());
		suite.addTestSuite(JavaBeanObservableSetTest.class);
		suite.addTest(MutableObservableSetContractTest.suite(new Delegate()));
		return suite;
