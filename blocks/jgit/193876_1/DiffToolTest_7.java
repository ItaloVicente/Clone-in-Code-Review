	private static String[] getExpectedCompareOutput(String[] conflictingFilenames) {
		List<String> expected = new ArrayList<>();
		int n = conflictingFilenames.length;
		for (int i = 0; i < n; ++i) {
			String newPath = conflictingFilenames[i];
			expected.add(
					"Viewing (" + (i + 1) + "/" + n + "): '" + newPath + "'");
			expected.add("Launch '" + TOOL_NAME + "' [Y/n]?");
			expected.add(newPath);
		}
		return expected.toArray(new String[0]);
	}

	private static String[] getExpectedAbortOutput(String[] conflictingFilenames
			int abortIndex) {
		List<String> expected = new ArrayList<>();
		int n = conflictingFilenames.length;
		for (int i = 0; i < n; ++i) {
			String newPath = conflictingFilenames[i];
			expected.add(
					"Viewing (" + (i + 1) + "/" + n + "): '" + newPath + "'");
			expected.add("Launch '" + TOOL_NAME + "' [Y/n]?");
			if (i == abortIndex) {
				break;
			}
			expected.add(newPath);
		}
		return expected.toArray(new String[0]);
