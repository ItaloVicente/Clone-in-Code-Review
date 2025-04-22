	public String getFirstAvailableTool() {
		String name = null;
		for (IDiffTool tool : predefinedTools.values()) {
			PreDefinedDiffTool predefTool = (PreDefinedDiffTool) tool;
			if (Utils.isToolAvailable(db
				name = predefTool.getName();
				break;
			}
		}
		return name;
