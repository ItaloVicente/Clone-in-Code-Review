	private ExternalMergeTool guessTool(String toolName
			throws ToolException {
		if ((toolName == null) || toolName.isEmpty()) {
			toolName = getDefaultToolName(gui);
		}
		ExternalMergeTool tool = getTool(toolName);
		if (tool == null) {
		}
		return tool;
	}

	private ExternalMergeTool getTool(final String name) {
		ExternalMergeTool tool = userDefinedTools.get(name);
		if (tool == null) {
			tool = predefinedTools.get(name);
		}
		return tool;
	}

