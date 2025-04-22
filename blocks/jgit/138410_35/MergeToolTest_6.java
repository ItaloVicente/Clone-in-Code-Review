	private static String[] getExpectedMergeConflictOutputNoPrompt(
			String[] conflictFilenames) {
		List<String> expected = new ArrayList<>();
		expected.add("Merging:");
		for (String conflictFilename : conflictFilenames) {
			expected.add(conflictFilename);
		}
		for (String conflictFilename : conflictFilenames) {
			expected.add("Normal merge conflict for '" + conflictFilename
					+ "':");
			expected.add("{local}: modified file");
			expected.add("{remote}: modified file");
			expected.add(conflictFilename);
			expected.add(conflictFilename + " seems unchanged.");
		}
		return expected.toArray(new String[0]);
	}

	private static String[] getExpectedAbortLaunchOutput(
			String[] conflictFilenames) {
		List<String> expected = new ArrayList<>();
		expected.add("Merging:");
		for (String conflictFilename : conflictFilenames) {
			expected.add(conflictFilename);
		}
		if (conflictFilenames.length > 1) {
			String conflictFilename = conflictFilenames[0];
			expected.add(
					"Normal merge conflict for '" + conflictFilename + "':");
			expected.add("{local}: modified file");
			expected.add("{remote}: modified file");
			expected.add("Hit return to start merge resolution tool ("
					+ TOOL_NAME + "):");
		}
		return expected.toArray(new String[0]);
	}

	private static String[] getExpectedAbortMergeOutput(
			String[] conflictFilenames
		List<String> expected = new ArrayList<>();
		expected.add("Merging:");
		for (String conflictFilename : conflictFilenames) {
			expected.add(conflictFilename);
		}
		for (int i = 0; i < conflictFilenames.length; ++i) {
			if (i == abortIndex) {
				break;
			}

			String conflictFilename = conflictFilenames[i];
			expected.add(
					"Normal merge conflict for '" + conflictFilename + "':");
			expected.add("{local}: modified file");
			expected.add("{remote}: modified file");
			expected.add("Hit return to start merge resolution tool ("
					+ TOOL_NAME + "): " + conflictFilename);
			expected.add(conflictFilename + " seems unchanged.");
			expected.add("Was the merge successful [y/n]?");
			if (i < conflictFilenames.length - 1) {
				expected.add(
						"\tContinue merging other unresolved paths [y/n]?");
			}
		}
		return expected.toArray(new String[0]);
	}

	private static String[] getExpectedMergeConflictOutput(
			String[] conflictFilenames) {
		List<String> expected = new ArrayList<>();
		expected.add("Merging:");
		for (String conflictFilename : conflictFilenames) {
			expected.add(conflictFilename);
		}
		for (int i = 0; i < conflictFilenames.length; ++i) {
			String conflictFilename = conflictFilenames[i];
			expected.add("Normal merge conflict for '" + conflictFilename
					+ "':");
			expected.add("{local}: modified file");
			expected.add("{remote}: modified file");
			expected.add("Hit return to start merge resolution tool ("
					+ TOOL_NAME + "): " + conflictFilename);
			expected.add(conflictFilename + " seems unchanged.");
			expected.add("Was the merge successful [y/n]?");
			if (i < conflictFilenames.length - 1) {
			}
		}
		return expected.toArray(new String[0]);
	}

	private static String[] getExpectedDeletedConflictOutput(
			String[] conflictFilenames) {
		List<String> expected = new ArrayList<>();
		expected.add("Merging:");
		for (String mergeConflictFilename : conflictFilenames) {
			expected.add(mergeConflictFilename);
