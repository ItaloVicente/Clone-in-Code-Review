	@RunWith(AllTests.class)
	public static class Suite {
		public static junit.framework.Test suite() {
			return new SuiteBuilder()
					.addObservableContractTest(UnchangeableObservableValueContractTest.class, new Delegate()).build();
		}
