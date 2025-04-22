	private String promptToolName() throws IOException {
		String toolNameToUse = toolName;
		if (StringUtils.isEmptyOrNull(toolNameToUse)) {
			toolNameToUse = diffTools.getDefaultToolName(gui);
		}
		if (StringUtils.isEmptyOrNull(toolNameToUse)) {
			Map<String, ExternalDiffTool> predefTools = diffTools
					.getPredefinedTools(false);
