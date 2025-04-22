	public void testMergeConflict() throws Exception {
		String[] inputLines = {
				"y"
				"y"
				"y"
				"y"
		};
		String[] conflictingFilenames = createMergeConflict();
		String[] expectedOutput = getExpectedMergeConflictOutput(
				conflictingFilenames);

		String option = "--tool";

		InputStream inputStream = createInputStream(inputLines);
		assertArrayOfLinesEquals("Incorrect output for option: " + option
				expectedOutput
						MERGE_TOOL
	}

	@Test
	public void testDeletedConflict() throws Exception {
		String[] inputLines = {
				"d"
				"m"
		};
		String[] conflictingFilenames = createDeletedConflict();
		String[] expectedOutput = getExpectedDeletedConflictOutput(
				conflictingFilenames);

		String option = "--tool";

		InputStream inputStream = createInputStream(inputLines);
		assertArrayOfLinesEquals("Incorrect output for option: " + option
				expectedOutput
						MERGE_TOOL
	}

	@Test
	public void testNoConflict() throws Exception {
		createStagedChanges();
		String[] expectedOutput = { "No files need merging" };
