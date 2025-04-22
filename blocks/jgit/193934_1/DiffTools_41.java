	public String getFirstAvailableTool() {
		for (ExternalDiffTool tool : predefinedTools.values()) {
			if (ExternalToolUtils.isToolAvailable(fs
					tool.getPath())) {
				return tool.getName();
			}
		}
		return null;
