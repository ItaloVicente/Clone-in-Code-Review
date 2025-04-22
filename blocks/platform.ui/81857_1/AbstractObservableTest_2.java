	@Test
	public void testSuite() throws Exception {
		JUnitCore.runClasses(Suite.class);
	}

	@RunWith(AllTests.class)
	public static class Suite {
		public static junit.framework.Test suite() {
			TestSuite suite = new TestSuite(AbstractObservableTest.class.getName());
			Delegate delegate = new Delegate();
			suite.addTest(ObservableContractTest.suite(delegate));
			suite.addTest(ObservableStaleContractTest.suite(delegate));
			return suite;
		}
