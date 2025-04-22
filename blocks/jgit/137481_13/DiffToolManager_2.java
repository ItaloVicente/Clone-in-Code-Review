		Map<String
		for (String name : userTools.keySet()) {
			IDiffTool userTool = userTools.get(name);
			if (userTool.getCommand() != null) {
				userDefinedTools.put(name
			} else if (userTool.getPath() != null) {
				PreDefinedDiffTool predefTool = (PreDefinedDiffTool) predefinedTools
						.get(name);
				if (predefTool != null) {
					predefTool.setPath(userTool.getPath());
				}
			}
		}
