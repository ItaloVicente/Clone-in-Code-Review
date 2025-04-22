	public String getFirstAvailableTool() {
		String name = null;
		for (ExternalDiffTool tool : predefinedTools.values()) {
			if (ExternalToolUtils.isToolAvailable(db
				name = tool.getName();
				break;
			}
		}
		return name;
