
		MergeTools diffTools = new MergeTools(db);
		Map<String
				.getPredefinedTools(true);
		List<ExternalMergeTool> availableTools = new ArrayList<>();
		List<ExternalMergeTool> notAvailableTools = new ArrayList<>();
		for (ExternalMergeTool tool : predefinedTools.values()) {
			if (tool.isAvailable()) {
				availableTools.add(tool);
			} else {
				notAvailableTools.add(tool);
			}
		}

