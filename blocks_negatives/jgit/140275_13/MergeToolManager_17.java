	/**
	 * @return id prompt enabled?
	 */
	public boolean isPrompt() {
		return config.isPrompt();
	}

	private ExternalMergeTool guessTool(String toolName, BooleanOption gui)
			throws ToolException {
		if ((toolName == null) || toolName.isEmpty()) {
			toolName = getDefaultToolName(gui);
		}
		ExternalMergeTool tool = null;
		if ((toolName != null) && !toolName.isEmpty()) {
			tool = getTool(toolName);
		}
		if (tool == null) {
		}
		return tool;
	}
