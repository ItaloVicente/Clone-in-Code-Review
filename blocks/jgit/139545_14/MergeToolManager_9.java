	public String getFirstAvailableTool() {
		String name = null;
		for (ExternalMergeTool tool : predefinedTools.values()) {
			if (ExternalToolUtils.isToolAvailable(db
				name = tool.getName();
				break;
			}
		}
		return name;
