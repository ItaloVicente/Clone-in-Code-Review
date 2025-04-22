	public String getFirstAvailableTool() {
		String name = null;
		for (ExternalDiffTool tool : predefinedTools.values()) {
			if (ExternalToolUtils.isToolAvailable(repo
				name = tool.getName();
				break;
			}
		}
		return name;
