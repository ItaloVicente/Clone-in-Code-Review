	private Pattern[] getExpectedCachedToolOutputNoPrompt(String[] conflictingFilenames) {
		String tmpDir = System.getProperty("java.io.tmpdir");
		if (tmpDir.endsWith(File.separator)) {
			tmpDir = tmpDir.substring(0
		}
		Pattern emptyPattern = Pattern.compile("");
		List<Pattern> expectedToolOutput = new ArrayList<>();
		for (int i = 0; i < conflictingFilenames.length; ++i) {
			String changedFilename = conflictingFilenames[i];
			Path fullPath = getFullPath(changedFilename);
			String filename = fullPath.getFileName().toString();
			String regexp = tmpDir + File.separatorChar + filename
					+ "_REMOTE_.*";
			Pattern pattern = Pattern.compile(regexp);
			expectedToolOutput.add(pattern);
			expectedToolOutput.add(emptyPattern);
		}
		expectedToolOutput.add(emptyPattern);
		return expectedToolOutput.toArray(new Pattern[0]);
	}

	private String[] getExpectedCompareOutput(String[] conflictingFilenames) {
		List<String> expected = new ArrayList<>();
		int n = conflictingFilenames.length;
		for (int i = 0; i < n; ++i) {
			String changedFilename = conflictingFilenames[i];
			expected.add(
					"Viewing (" + (i + 1) + "/" + n + "): '" + changedFilename
							+ "'");
			expected.add("Launch '" + TOOL_NAME + "' [Y/n]?");
			Path fullPath = getFullPath(changedFilename);
			expected.add(fullPath.toString());
		}
		return expected.toArray(new String[0]);
	}

	private String[] getExpectedAbortOutput(String[] conflictingFilenames
			int abortIndex) {
		List<String> expected = new ArrayList<>();
		int n = conflictingFilenames.length;
		for (int i = 0; i < n; ++i) {
			String changedFilename = conflictingFilenames[i];
			expected.add(
					"Viewing (" + (i + 1) + "/" + n + "): '" + changedFilename
							+ "'");
			expected.add("Launch '" + TOOL_NAME + "' [Y/n]?");
			if (i == abortIndex) {
				break;
			}
			Path fullPath = getFullPath(changedFilename);
			expected.add(fullPath.toString());
		}
		return expected.toArray(new String[0]);
	}

	private static String getEchoCommand() {
		return "(echo \"$REMOTE\")";
