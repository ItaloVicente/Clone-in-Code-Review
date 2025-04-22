	public static Test suite() {
		return new BindingTestSuite();
	}
	
	public BindingTestSuite() {
		addTestSuite(BindingLookupTest.class);
		addTestSuite(KeyDispatcherTest.class);
		addTestSuite(BindingTableTests.class);
		addTestSuite(BindingCreateTest.class);
	}
