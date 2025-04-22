	/**
	 * Construct the test suite.
	 */
	public DynamicPluginsTestSuite() {
		addTest(new TestSuite(StatusHandlerTests.class));
		addTest(new TestSuite(
				AcceleratorConfigurationsExtensionDynamicTest.class));
		addTest(new TestSuite(AcceleratorScopesExtensionDynamicTest.class));
		addTest(new TestSuite(ActionDefinitionsExtensionDynamicTest.class));
		addTest(new TestSuite(ActionSetTests.class));
		addTest(new TestSuite(ActivitySupportTests.class));
		addTest(new TestSuite(BindingsExtensionDynamicTest.class));
		addTest(new TestSuite(BrowserTests.class));
		addTest(new TestSuite(CommandsExtensionDynamicTest.class));
		addTest(new TestSuite(ContextsExtensionDynamicTest.class));
		addTest(new TestSuite(HandlersExtensionDynamicTest.class));
		addTest(new TestSuite(PreferencePageTests.class));
		addTest(new TestSuite(KeywordTests.class));
		addTest(new TestSuite(PropertyPageTests.class));
		addTest(new TestSuite(HelpSupportTests.class));
		addTest(new TestSuite(EncodingTests.class));
		addTest(new TestSuite(DecoratorTests.class));
		addTest(new TestSuite(StartupTests.class));
		addTest(new TestSuite(EditorTests.class));
		addTest(new TestSuite(IntroTests.class));
		addTest(new TestSuite(PerspectiveTests.class));
		addTest(new TestSuite(ViewTests.class));
		addTest(new TestSuite(NewWizardTests.class));
		addTest(new TestSuite(ObjectContributionTests.class));
		addTest(WorkingSetTests.suite());
		addTest(new TestSuite(DynamicSupportTests.class));
		addTest(new TestSuite(DynamicContributionTest.class));
		addTest(new TestSuite(DynamicInvalidContributionTest.class));
		addTest(new TestSuite(DynamicInvalidControlContributionTest.class));
	}
