	@Test(expected = Die.class)
	public void testUndefinedTool() throws Exception {
		String toolName = "undefined";
		RevCommit commit = createUnstagedChanges();
		List<DiffEntry> changes = getRepositoryChanges(commit);

		List<String> expectedErrors = new ArrayList<>();
		for (DiffEntry change : changes) {
			String changedFilename = change.getNewPath();
			expectedErrors.add("External diff tool is not defined: " + toolName);
			expectedErrors.add("compare of " + changedFilename + " failed");
		}

		runAndCaptureUsingInitRaw(expectedErrors
				"--tool"
		fail("Expected exception to be thrown due to undefined external tool");
	}

	@Test(expected = Die.class)
	public void testUserToolWithCommandNotFoundError() throws Exception {
		String toolName = "customTool";

		String command = "exit " + errorReturnCode;

		StoredConfig config = db.getConfig();
		config.setString(CONFIG_DIFFTOOL_SECTION
				command);

		createMergeConflict();
		runAndCaptureUsingInitRaw(DIFF_TOOL

		fail("Expected exception to be thrown due to external tool exiting with error code: "
				+ errorReturnCode);
	}

	@Test
	public void testEmptyToolName() throws Exception {
		String emptyToolName = "";

		StoredConfig config = db.getConfig();
		String subsection = null;
		config.setString(CONFIG_DIFF_SECTION
				emptyToolName);

		createUnstagedChanges();

		String araxisErrorLine = "compare: unrecognized option `-wait' @ error/compare.c/CompareImageCommand/1123.";
		String[] expectedErrorOutput = { araxisErrorLine
		runAndCaptureUsingInitRaw(Arrays.asList(expectedErrorOutput)
				"--no-prompt");
	}

