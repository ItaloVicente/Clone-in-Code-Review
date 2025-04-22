	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTestSuite(TabbedPropertySheetPageTest.class);
		suite.addTestSuite(TabbedPropertySheetPageDynamicTest.class);
		suite.addTestSuite(TabbedPropertySheetPageTextTest.class);
		suite.addTestSuite(TabbedPropertySheetPageOverrideTest.class);
		suite.addTestSuite(TabbedPropertySheetPageDecorationsTest.class);
		return suite;
	}
