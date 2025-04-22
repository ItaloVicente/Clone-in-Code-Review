	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for org.eclipse.ui.browser.tests");
		suite.addTestSuite(ExistenceTestCase.class);
		suite.addTestSuite(InternalBrowserViewTestCase.class);
		suite.addTestSuite(InternalBrowserEditorTestCase.class);

		suite.addTestSuite(DialogsTestCase.class);
		suite.addTestSuite(PreferencesTestCase.class);
		suite.addTestSuite(ToolbarBrowserTestCase.class);
		suite.addTestSuite(WebBrowserUtilTestCase.class);
		return suite;
	}
