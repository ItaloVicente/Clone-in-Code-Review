	public String getFirstAvailableTool() {
		String name = null;
		for (IMergeTool tool : predefinedTools.values()) {
			if (Utils.isToolAvailable(db
				name = tool.getName();
				break;
			}
		}
		return name;
