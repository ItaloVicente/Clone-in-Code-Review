	private String getToolNameToUse() throws IOException {
		String toolNameToUse = toolName;
		if ((toolNameToUse == null) || toolNameToUse.isEmpty()) {
			toolNameToUse = mergeTools.getDefaultToolName(gui);
		}
		if ((toolNameToUse == null) || toolNameToUse.isEmpty()) {
