	private ExternalMergeTool guessTool(String toolName, Optional<Boolean> gui)
			throws ToolException {
		if (StringUtils.isEmptyOrNull(toolName)) {
			toolName = getDefaultToolName(gui);
		}
		ExternalMergeTool tool = null;
		if (StringUtils.isEmptyOrNull(toolName)) {
			tool = getTool(toolName);
		}
		if (tool == null) {
		}
		return tool;
