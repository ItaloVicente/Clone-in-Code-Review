		String command = "exit " + errorReturnCode;

		StoredConfig config = db.getConfig();
		config.setString(CONFIG_DIFFTOOL_SECTION
				command);

		createMergeConflict();
		runAndCaptureUsingInitRaw(DIFF_TOOL

		fail("Expected exception to be thrown due to external tool exiting with error code: "
				+ errorReturnCode);
	}

	@Test(expected = Die.class)
	public void testEmptyToolName() throws Exception {
		assumeLinuxPlatform();

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
		fail("Expected exception to be thrown due to external tool exiting with an error");
	}

	@Test
	public void testToolWithPrompt() throws Exception {
		String[] inputLines = {
				"y"
				"y"
		};

		String[] conflictingFilenames = createUnstagedChanges();
		String[] expectedOutput = getExpectedCompareOutput(conflictingFilenames);

		String option = "--tool";

		InputStream inputStream = createInputStream(inputLines);
		assertArrayOfLinesEquals("Incorrect output for option: " + option
				expectedOutput
						DIFF_TOOL
	}

	@Test
	public void testToolAbortLaunch() throws Exception {
		String[] inputLines = {
				"y"
				"n"
		};

		String[] conflictingFilenames = createUnstagedChanges();
		int abortIndex = 1;
		String[] expectedOutput = getExpectedAbortOutput(conflictingFilenames

		String option = "--tool";

		InputStream inputStream = createInputStream(inputLines);
		assertArrayOfLinesEquals("Incorrect output for option: " + option
				expectedOutput
				runAndCaptureUsingInitRaw(inputStream
						TOOL_NAME));
	}

	@Test(expected = Die.class)
	public void testNotDefinedTool() throws Exception {
		createUnstagedChanges();

		runAndCaptureUsingInitRaw(DIFF_TOOL
		fail("Expected exception when trying to run undefined tool");
