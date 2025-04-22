	public Map<String
		return Collections.unmodifiableMap(userDefinedTools);
	}

	public Map<String
			boolean checkAvailability) {
		if (checkAvailability) {
			for (ExternalMergeTool tool : predefinedTools.values()) {
				PreDefinedMergeTool predefTool = (PreDefinedMergeTool) tool;
				predefTool.setAvailable(ExternalToolUtils.isToolAvailable(fs
						gitDir
			}
		}
		return Collections.unmodifiableMap(predefinedTools);
	}

	public String getFirstAvailableTool() {
		String name = null;
		for (ExternalMergeTool tool : predefinedTools.values()) {
			if (ExternalToolUtils.isToolAvailable(fs
					tool.getPath())) {
				name = tool.getName();
				break;
			}
		}
		return name;
	}

