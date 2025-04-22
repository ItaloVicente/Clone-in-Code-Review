	@Test
	public void testExternalMergeToolGitConfig() throws Exception {
		assumePosixPlatform();

		createMergeConflict();

		int runs = 2;
		for (int i = 0; i < runs; ++i) {
			clearResultFile();

			String toolName = "custom_tool" + i;
			configureEchoTool(toolName);
			preferenceNode.putInt(UIPreferences.MERGE_TOOL_MODE,
					MergeToolMode.GIT_CONFIG.ordinal());
			preferenceNode.put(UIPreferences.MERGE_TOOL_CUSTOM, toolName);

			triggerMergeToolAction();

			List<String> commandOutputLines = waitForToolOutput();

			String actualCommandOutput = String.join(System.lineSeparator(),
					commandOutputLines);
			String expectedOutputPattern = toolName + " .*.txt";
			boolean matchingOutput = Pattern.matches(expectedOutputPattern,
					actualCommandOutput);
			assertTrue("Command output doesn't match expected pattern: "
					+ expectedOutputPattern + ", command output: "
					+ actualCommandOutput, matchingOutput);
		}
	}

