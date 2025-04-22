	@Test
	public void testSuite() throws Exception {
		JUnitCore.runClasses(Suite.class);
	}

	@RunWith(AllTests.class)
	public static class Suite {
		public static junit.framework.Test suite() {
			TestSuite suite = new TestSuite(ObservableListTest.class.getName());
			suite.addTest(ObservableListContractTest.suite(new Delegate()));
			return suite;
		}
