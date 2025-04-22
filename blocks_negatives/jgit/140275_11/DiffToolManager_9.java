		for (PreDefinedDiffTools tool : PreDefinedDiffTools.values()) {
			tools.put(tool.name(), new PreDefinedDiffTool(tool.name(),
					tool.getPath(), tool.getParameters()));
		}
		return tools;
	}
