	@Test
	public void testExternalDiffToolGitConfig() throws Exception {
		assumePosixPlatform();

		createMergeConflict();

		int runs = 2;
		for (int i = 0; i < runs; ++i) {
			clearResultFile();

			String toolName = "custom_tool" + i;
			configureEchoTool(toolName);
			preferenceNode.putInt(UIPreferences.DIFF_TOOL_MODE,
					DiffToolMode.GIT_CONFIG.ordinal());
			preferenceNode.put(UIPreferences.DIFF_TOOL_CUSTOM, toolName);

			triggerCompareWithPreviousAction();

			List<String> commandOutputLines = waitForToolOutput();

			String actualCommandOutput = String.join(System.lineSeparator(),
					commandOutputLines);
			String expectedOutputPattern = toolName
					+ " local=.*.txt remote=.*.txt";
			boolean matchingOutput = Pattern.matches(expectedOutputPattern,
					actualCommandOutput);
			assertTrue("Command output doesn't match expected pattern: "
					+ expectedOutputPattern + ", command output: "
					+ actualCommandOutput, matchingOutput);
		}
	}

