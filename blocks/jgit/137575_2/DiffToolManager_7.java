	private ITool guessTool(String toolName
			throws DiffToolException {
		if (toolName == null) {
			toolName = gui.toBoolean() ? config.getDefaultGuiToolName()
					: config.getDefaultToolName();
		}
		ITool tool = getTool(toolName);
		if (tool == null) {
		}
		return tool;
	}

	private ITool getTool(final String name) {
		ITool tool = userDefinedTools.get(name);
		if (tool == null) {
			tool = predefinedTools.get(name);
		}
		return tool;
	}

	private Map<String
		Map<String
