	public static Test suite() {
		TestSuite suite = new TestSuite(DecoratingObservableSetTest.class
				.getName());
		suite.addTest(MutableObservableCollectionContractTest
				.suite(new Delegate()));
		return suite;
