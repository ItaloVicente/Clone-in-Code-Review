		for (int i = 0; i < conflictFilenames.length; ++i) {
			String conflictFilename = conflictFilenames[i];
			expected.add(conflictFilename + " seems unchanged.");
			expected.add("{local}: deleted");
			expected.add("{remote}: modified file");
			expected.add("Use (m)odified or (d)eleted file
