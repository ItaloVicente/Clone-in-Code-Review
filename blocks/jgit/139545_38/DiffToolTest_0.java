
		DiffTools diffTools = new DiffTools(db);
		Map<String
				.getPredefinedTools(true);
		List<ExternalDiffTool> availableTools = new ArrayList<>();
		List<ExternalDiffTool> notAvailableTools = new ArrayList<>();
		for (ExternalDiffTool tool : predefinedTools.values()) {
			if (tool.isAvailable()) {
				availableTools.add(tool);
			} else {
				notAvailableTools.add(tool);
			}
		}

