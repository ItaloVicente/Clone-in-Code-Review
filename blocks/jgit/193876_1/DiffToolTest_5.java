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
