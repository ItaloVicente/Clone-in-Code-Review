	private ExternalDiffTool guessTool(String toolName, BooleanOption gui)
			throws ToolException {
		if ((toolName == null) || toolName.isEmpty()) {
			toolName = getDefaultToolName(gui);
		}
		ExternalDiffTool tool = null;
		if ((toolName != null) && !toolName.isEmpty()) {
			tool = getTool(toolName);
		}
		if (tool == null) {
		}
		return tool;
	}
