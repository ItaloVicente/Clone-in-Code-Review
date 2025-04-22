
		DiffTools diffTools = new DiffTools(db);
		Map<String
				.getPredefinedTools(true);
		List<ExternalDiffTool> availableTools = new ArrayList<>();
		List<ExternalDiffTool> notAvailableTools = new ArrayList<>();
		for (ExternalDiffTool tool : predefinedTools.values()) {
			if (tool.isAvailable()) {
				availableTools.add(tool);
			} else {
				notAvailableTools.add(tool);
			}
		}

		expectedOutput.add(
				"'git difftool --tool=<tool>' may be set to one of the following:");
		for (ExternalDiffTool tool : availableTools) {
			String toolName = tool.getName();
			expectedOutput.add(toolName);
		}
		String customToolHelpLine = TOOL_NAME + "." + CONFIG_KEY_CMD + " "
				+ getEchoCommand();
		expectedOutput.add("user-defined:");
		expectedOutput.add(customToolHelpLine);
		expectedOutput.add(
				"The following tools are valid
		for (ExternalDiffTool tool : notAvailableTools) {
			String toolName = tool.getName();
