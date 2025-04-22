		for (CommandLineDiffTool tool : CommandLineDiffTool.values()) {
			predefinedTools
					.put(tool.name()
							new PreDefinedDiffTool(tool.name()
									tool.getParameters()));
		}
