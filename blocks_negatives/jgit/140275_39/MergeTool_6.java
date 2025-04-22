	private String promptToolName() throws IOException {
		String toolNameToUse = toolName;
		if (StringUtils.isEmptyOrNull(toolNameToUse)) {
			toolNameToUse = mergeTools.getDefaultToolName(gui);
		}
		if (StringUtils.isEmptyOrNull(toolNameToUse)) {
			Map<String, ExternalMergeTool> predefTools = mergeTools
					.getPredefinedTools(false);
