	private String promptToolName() throws IOException {
		String toolNameToUse = toolName;
		if (StringUtils.isEmptyOrNull(toolNameToUse)) {
			toolNameToUse = diffTools.getDefaultToolName(gui);
		}
		if (StringUtils.isEmptyOrNull(toolNameToUse)) {
			Map<String
					.getPredefinedTools(false);
			StringBuilder toolNames = new StringBuilder();
			for (String name : predefTools.keySet()) {
			}
			outw.println(MessageFormat.format(
					CLIText.get().diffToolPromptToolName
			outw.flush();
			toolNameToUse = diffTools.getFirstAvailableTool();
		}
		if (StringUtils.isEmptyOrNull(toolNameToUse)) {
			throw new IOException(MessageFormat
					.format(CLIText.get().diffToolUnknownToolName
		}
		return toolNameToUse;
	}

