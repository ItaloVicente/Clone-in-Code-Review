	@Test
	public void testToolWithPrompt() throws Exception {
		String[] inputLines = {
				"y"
				"y"
		};

		RevCommit commit = createUnstagedChanges();
		List<DiffEntry> changes = getRepositoryChanges(commit);
		String[] expectedOutput = getExpectedCompareOutput(changes);

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

		RevCommit commit = createUnstagedChanges();
		List<DiffEntry> changes = getRepositoryChanges(commit);
		int abortIndex = 1;
		String[] expectedOutput = getExpectedAbortOutput(changes

		String option = "--tool";

		InputStream inputStream = createInputStream(inputLines);
		assertArrayOfLinesEquals("Incorrect output for option: " + option
				expectedOutput
				runAndCaptureUsingInitRaw(inputStream
						TOOL_NAME));
	}

