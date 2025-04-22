		CommandLineDiffTool[] defaultTools = CommandLineDiffTool.values();
		List<String> expectedOutput = new ArrayList<>();
		expectedOutput.add("git difftool --tool=<tool> may be set to one of the following:");
		for (CommandLineDiffTool defaultTool : defaultTools) {
			String toolName = defaultTool.name();
			expectedOutput.add(toolName);
		}
		String[] userDefinedToolsHelp = {
