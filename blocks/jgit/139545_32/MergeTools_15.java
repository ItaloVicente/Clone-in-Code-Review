	public String getFirstAvailableTool() {
		String name = null;
		for (ExternalMergeTool tool : predefinedTools.values()) {
			if (ExternalToolUtils.isToolAvailable(repo
				name = tool.getName();
				break;
			}
		}
		return name;
