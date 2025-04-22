	public static Test suite() {
		TestSuite suite = new TestSuite(
				JavaBeanObservableArrayBasedListTest.class.getName());
		suite.addTestSuite(JavaBeanObservableArrayBasedListTest.class);
		suite.addTest(MutableObservableListContractTest.suite(new Delegate()));
		return suite;
