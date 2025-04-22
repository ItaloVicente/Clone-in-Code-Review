		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		return new AllTests();
	}

	public AllTests() {
		addTestSuite(BooleanFieldEditorTest.class);
		addTestSuite(StringFieldEditorTest.class);
		addTestSuite(IntegerFieldEditorTest.class);
