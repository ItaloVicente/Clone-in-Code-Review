	private String getToolNameToUse() throws IOException {
		String toolNameToUse = toolName;
		if ((toolNameToUse == null) || toolNameToUse.isEmpty()) {
			toolNameToUse = diffToolMgr.getDefaultToolName(gui);
		}
		if ((toolNameToUse == null) || toolNameToUse.isEmpty()) {
			outw.println(
			outw.println(
			outw.println(
			Map<String
					.getPredefinedTools(false);
			for (String name : predefTools.keySet()) {
			}
			outw.println();
			outw.flush();
			toolNameToUse = diffToolMgr.getFirstAvailableTool();
		}
		if ((toolNameToUse == null) || toolNameToUse.isEmpty()) {
		}
		return toolNameToUse;
	}

