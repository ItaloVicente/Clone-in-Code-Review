
	private static String[] getExpectedCompareOutput(List<DiffEntry> changes) {
		List<String> expected = new ArrayList<>();
		int n = changes.size();
		for (int i = 0; i < n; ++i) {
			DiffEntry change = changes.get(i);
			String newPath = change.getNewPath();
			expected.add(
					"Viewing (" + (i + 1) + "/" + n + "): '" + newPath + "'");
			expected.add("Launch '" + TOOL_NAME + "' [Y/n]?");
			expected.add(newPath);
		}
		return expected.toArray(new String[0]);
	}

	private static String[] getExpectedAbortOutput(List<DiffEntry> changes
			int abortIndex) {
		List<String> expected = new ArrayList<>();
		int n = changes.size();
		for (int i = 0; i < n; ++i) {
			DiffEntry change = changes.get(i);
			String newPath = change.getNewPath();
			expected.add(
					"Viewing (" + (i + 1) + "/" + n + "): '" + newPath + "'");
			expected.add("Launch '" + TOOL_NAME + "' [Y/n]?");
			if (i == abortIndex) {
				break;
			}
			expected.add(newPath);
		}
		return expected.toArray(new String[0]);
	}
