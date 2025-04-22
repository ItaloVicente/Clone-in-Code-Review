	public static Test suite() {
		TestSuite suite = new TestSuite(
				JavaBeanObservableArrayBasedSetTest.class.getName());
		suite.addTestSuite(JavaBeanObservableArrayBasedSetTest.class);
		suite.addTest(MutableObservableSetContractTest.suite(new Delegate()));
		return suite;
