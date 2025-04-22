	public Map<String
			boolean checkAvailability) {
		if (checkAvailability) {
			for (ExternalMergeTool tool : predefinedTools.values()) {
				PreDefinedMergeTool predefTool = (PreDefinedMergeTool) tool;
				predefTool.setAvailable(ExternalToolUtils.isToolAvailable(db
						predefTool.getPath()));
			}
		}
