	@Test
	public void testUndefinedTool() throws Exception {
		String toolName = "undefined";
		String[] conflictingFilenames = createMergeConflict();

		List<String> expectedErrors = new ArrayList<>();
		for (String conflictingFilename : conflictingFilenames) {
			expectedErrors.add("External merge tool is not defined: " + toolName);
			expectedErrors.add("merge of " + conflictingFilename + " failed");
		}

		runAndCaptureUsingInitRaw(expectedErrors
				"--no-prompt"
	}

	@Test(expected = Die.class)
	public void testUserToolWithCommandNotFoundError() throws Exception {
		String toolName = "customTool";

		String command = "exit " + errorReturnCode;

		StoredConfig config = db.getConfig();
		config.setString(CONFIG_MERGETOOL_SECTION
				command);

		createMergeConflict();
		runAndCaptureUsingInitRaw(MERGE_TOOL
				toolName);

		fail("Expected exception to be thrown due to external tool exiting with error code: "
				+ errorReturnCode);
	}

	@Test
	public void testEmptyToolName() throws Exception {
		String emptyToolName = "";

		StoredConfig config = db.getConfig();
		String subsection = null;
		config.setString(CONFIG_MERGE_SECTION
				emptyToolName);

		createMergeConflict();

		String araxisErrorLine = "compare: unrecognized option `-wait' @ error/compare.c/CompareImageCommand/1123.";
		String[] expectedErrorOutput = { araxisErrorLine
		runAndCaptureUsingInitRaw(Arrays.asList(expectedErrorOutput)
				MERGE_TOOL
	}

