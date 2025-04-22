				expectedOutput.toArray(new String[0])
				runAndCaptureUsingInitRaw(DIFF_TOOL
	}

	private void configureEchoTool(String toolName) {
		StoredConfig config = db.getConfig();
		String subsection = null;
		config.setString(CONFIG_DIFF_SECTION
				toolName);

		String command = getEchoCommand();

		config.setString(CONFIG_DIFFTOOL_SECTION
				command);
		config.setString(CONFIG_DIFFTOOL_SECTION
				String.valueOf(false));
	}

	private String[] getExpectedToolOutputNoPrompt(String[] conflictingFilenames) {
		String[] expectedToolOutput = new String[conflictingFilenames.length];
		for (int i = 0; i < conflictingFilenames.length; ++i) {
			String newPath = conflictingFilenames[i];
			Path fullPath = getFullPath(newPath);
			expectedToolOutput[i] = fullPath.toString();
