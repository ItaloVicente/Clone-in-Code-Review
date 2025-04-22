	public static Test suite() {
		TestSuite suite = new TestSuite(JavaBeanObservableListTest.class
				.getName());
		suite.addTestSuite(JavaBeanObservableListTest.class);
		suite.addTest(MutableObservableListContractTest.suite(new Delegate()));
		return suite;
