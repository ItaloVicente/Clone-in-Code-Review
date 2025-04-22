	public Map<String
			boolean checkAvailability) {
		if (checkAvailability) {
			for (IMergeTool tool : predefinedTools.values()) {
				PreDefinedDiffTool predefTool = (PreDefinedDiffTool) tool;
				predefTool.setAvailable(
						Utils.isToolAvailable(db
			}
		}
