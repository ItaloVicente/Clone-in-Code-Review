		invokeMerge(toolName);
		fail("Expected exception to be thrown due to not defined external merge tool");
	}

	private Optional<ExecutionResult> invokeMerge(String toolName)
			throws ToolException {
