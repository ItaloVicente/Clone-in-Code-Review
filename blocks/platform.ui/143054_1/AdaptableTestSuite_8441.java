	public AdaptableTestSuite() {
		addTest(AdaptableDecoratorTestCase.suite());
		addTest(new TestSuite(MarkerImageProviderTest.class));
		addTest(new TestSuite(WorkingSetTestCase.class));
		addTest(new TestSuite(SelectionAdapterTest.class));
	}
