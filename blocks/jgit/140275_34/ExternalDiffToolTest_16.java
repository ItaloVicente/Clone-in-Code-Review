		String toolName = "undefined";
		invokeCompare(toolName);
		fail("Expected exception to be thrown due to not defined external diff tool");
	}

	private Optional<ExecutionResult> invokeCompare(String toolName)
			throws ToolException {
