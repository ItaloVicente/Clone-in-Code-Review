	@Test
	public void testSuite() throws Exception {
		JUnitCore.runClasses(Suite.class);
	}

	@RunWith(AllTests.class)
	public static class Suite {
		public static junit.framework.Test suite() {
			TestSuite suite = new TestSuite(MapEntryObservableValueTest.class.getName());
			suite.addTest(MutableObservableValueContractTest.suite(new Delegate()));
			suite.addTest(ObservableStaleContractTest.suite(new Delegate()));
			return suite;
		}
