	private Pattern[] getExpectedCachedToolOutputNoPrompt(String[] conflictingFilenames) {
		String tmpDir = System.getProperty("java.io.tmpdir");
		Pattern emptyPattern = Pattern.compile("");
		List<Pattern> expectedToolOutput = new ArrayList<>();
		for (int i = 0; i < conflictingFilenames.length; ++i) {
			String changedFilename = conflictingFilenames[i];
			String regexp = tmpDir + File.separatorChar + changedFilename
					+ "_REMOTE_.*";
			Pattern pattern = Pattern.compile(regexp);
			expectedToolOutput.add(pattern);
			expectedToolOutput.add(emptyPattern);
		}
		expectedToolOutput.add(emptyPattern);
		return expectedToolOutput.toArray(new Pattern[0]);
	}

	private String[] getExpectedCompareOutput(String[] conflictingFilenames) {
