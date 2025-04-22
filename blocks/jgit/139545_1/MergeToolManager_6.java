	public String getFirstAvailableTool() {
		String name = null;
		for (IMergeTool tool : predefinedTools.values()) {
			PreDefinedMergeTool predefTool = (PreDefinedMergeTool) tool;
			if (Utils.isToolAvailable(db
				name = predefTool.getName();
				break;
			}
		}
		return name;
