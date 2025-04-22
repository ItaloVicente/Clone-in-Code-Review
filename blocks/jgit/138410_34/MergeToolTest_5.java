	@Test
	public void testMergeConflictNoPrompt() throws Exception {
		String[] conflictingFilenames = createMergeConflict();
		String[] expectedOutput = getExpectedMergeConflictOutputNoPrompt(
				conflictingFilenames);

		String option = "--tool";

		assertArrayOfLinesEquals("Incorrect output for option: " + option
				expectedOutput
				runAndCaptureUsingInitRaw(MERGE_TOOL
	}

	@Test
	public void testMergeConflictNoGuiNoPrompt() throws Exception {
		String[] conflictingFilenames = createMergeConflict();
		String[] expectedOutput = getExpectedMergeConflictOutputNoPrompt(
				conflictingFilenames);

		String option = "--tool";

		assertArrayOfLinesEquals("Incorrect output for option: " + option
				expectedOutput
						"--no-gui"
	}

