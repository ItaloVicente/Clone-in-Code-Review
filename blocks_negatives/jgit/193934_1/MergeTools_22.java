	private ExternalMergeTool guessTool(String toolName, BooleanTriState gui)
			throws ToolException {
		if ((toolName == null) || toolName.isEmpty()) {
			toolName = getDefaultToolName(gui);
		}
		ExternalMergeTool tool = getTool(toolName);
		if (tool == null) {
		}
		return tool;
