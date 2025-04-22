	private ExternalDiffTool guessTool(String toolName, BooleanTriState gui)
			throws ToolException {
		if (StringUtils.isEmptyOrNull(toolName)) {
			toolName = getDefaultToolName(gui);
		}
		ExternalDiffTool tool = null;
		if (!StringUtils.isEmptyOrNull(toolName)) {
			tool = getTool(toolName);
		}
		if (tool == null) {
		}
		return tool;
	}

