	public void testAbortMerge() throws Exception {
		String[] inputLines = {
				"y"
				"n"
				"n"
		};
		String[] conflictingFilenames = createMergeConflict();
		int abortIndex = 1;
		String[] expectedOutput = getExpectedAbortMergeOutput(
				conflictingFilenames
				abortIndex);

		String option = "--tool";

		InputStream inputStream = createInputStream(inputLines);
		assertArrayOfLinesEquals("Incorrect output for option: " + option
				expectedOutput
						MERGE_TOOL
	}
