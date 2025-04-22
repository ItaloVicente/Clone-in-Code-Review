		Set<String> expectedToolNames = new LinkedHashSet<>();
		CommandLineDiffTool[] defaultTools = CommandLineDiffTool.values();
		for (CommandLineDiffTool defaultTool : defaultTools) {
			String toolName = defaultTool.name();
			expectedToolNames.add(toolName);
		}
		assertEquals("Incorrect set of external diff tools"
