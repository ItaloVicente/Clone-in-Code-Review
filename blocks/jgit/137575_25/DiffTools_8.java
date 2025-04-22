	public String getDefaultToolName(BooleanOption gui) {
		return gui.toBoolean() ? config.getDefaultGuiToolName()
				: config.getDefaultToolName();
	}

	public boolean isPrompt() {
		return config.isPrompt();
	}

	private ExternalDiffTool guessTool(String toolName
			throws ToolException {
		if ((toolName == null) || toolName.isEmpty()) {
			toolName = getDefaultToolName(gui);
		}
		ExternalDiffTool tool = getTool(toolName);
		if (tool == null) {
		}
		return tool;
	}

	private ExternalDiffTool getTool(final String name) {
		ExternalDiffTool tool = userDefinedTools.get(name);
		if (tool == null) {
			tool = predefinedTools.get(name);
		}
		return tool;
	}

	private Map<String
		Map<String
