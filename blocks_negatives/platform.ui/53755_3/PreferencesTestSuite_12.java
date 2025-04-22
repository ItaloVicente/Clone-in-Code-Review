	/**
	 * Construct the test suite.
	 */
	public PreferencesTestSuite() {
		addTest(new TestSuite(FontPreferenceTestCase.class));
		addTest(new TestSuite(DeprecatedFontPreferenceTestCase.class));
		addTest(new TestSuite(ScopedPreferenceStoreTestCase.class));
		addTest(new TestSuite(WorkingCopyPreferencesTestCase.class));
		addTest(new TestSuite(PropertyPageEnablementTest.class));
		addTest(new TestSuite(ListenerRemovalTestCase.class));
		addTest(new TestSuite(PreferencesDialogTest.class));
	}
