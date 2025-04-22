		for (String mergeConflictFilename : mergeConflictFilenames) {
			expectedOutput.add("Normal merge conflict for '"
					+ mergeConflictFilename + "':");
			expectedOutput.add("{local}: modified file");
			expectedOutput.add("{remote}: modified file");
			expectedOutput.add("TODO: Launch mergetool '" + TOOL_NAME
					+ "' for path '" + mergeConflictFilename + "'...");
