	private String getToolNameToUse() throws IOException {
		String toolNameToUse = toolName;
		if ((toolNameToUse == null) || toolNameToUse.isEmpty()) {
			toolNameToUse = diffToolMgr.getDefaultToolName(gui);
		}
		if ((toolNameToUse == null) || toolNameToUse.isEmpty()) {
