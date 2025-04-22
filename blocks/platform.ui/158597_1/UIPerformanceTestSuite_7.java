		addTest(new JUnit4TestAdapter(OpenCloseEditorTest.class));
		addTest(new JUnit4TestAdapter(OpenMultipleEditorTest.class));
		addTest(new JUnit4TestAdapter(EditorSwitchTest.class));
		addTestSuite(CommandsPerformanceTest.class);
		addTest(new JUnit4TestAdapter(LabelProviderTest.class));
		addTestSuite(ProgressReportingTest.class);
		addTestSuite(OpenNavigatorFolderTest.class);
