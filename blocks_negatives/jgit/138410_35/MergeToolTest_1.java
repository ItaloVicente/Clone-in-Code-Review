	private String[] getExpectedToolOutput() {
		String[] mergeConflictFilenames = { "a", "b", };
		List<String> expectedOutput = new ArrayList<>();
		expectedOutput.add("Merging:");
		for (String mergeConflictFilename : mergeConflictFilenames) {
			expectedOutput.add(mergeConflictFilename);
