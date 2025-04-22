	public String getDefaultToolName(BooleanOption gui) {
		return gui.toBoolean() ? config.getDefaultGuiToolName()
				: config.getDefaultToolName();
	}

	public boolean isPrompt() {
		return config.isPrompt();
	}

	private IDiffTool guessTool(String toolName
			throws ToolException {
		if ((toolName == null) || toolName.isEmpty()) {
			toolName = getDefaultToolName(gui);
		}
		IDiffTool tool = getTool(toolName);
		if (tool == null) {
		}
		return tool;
	}

	private IDiffTool getTool(final String name) {
		IDiffTool tool = userDefinedTools.get(name);
		if (tool == null) {
			tool = predefinedTools.get(name);
		}
		return tool;
	}

	private Map<String
		Map<String
