	public Map<String
			boolean checkAvailability) {
		if (checkAvailability) {
			for (ExternalDiffTool tool : predefinedTools.values()) {
				PreDefinedDiffTool predefTool = (PreDefinedDiffTool) tool;
				predefTool.setAvailable(ExternalToolUtils.isToolAvailable(fs
						gitDir
			}
		}
