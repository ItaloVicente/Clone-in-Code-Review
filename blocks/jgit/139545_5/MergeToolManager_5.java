	public Map<String
			boolean checkAvailability) {
		if (checkAvailability) {
			for (IMergeTool tool : predefinedTools.values()) {
				PreDefinedMergeTool predefTool = (PreDefinedMergeTool) tool;
				predefTool.setAvailable(
						Utils.isToolAvailable(db
			}
		}
