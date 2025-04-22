
	@Test
	public void testOverridePreDefinedToolPath() {
		String newToolPath = "/tmp/path/";

		CommandLineDiffTool[] defaultTools = CommandLineDiffTool.values();
		assertTrue("Expected to find pre-defined external diff tools"
				defaultTools.length > 0);

		CommandLineDiffTool overridenTool = defaultTools[0];
		String overridenToolName = overridenTool.name();
		String overridenToolPath = newToolPath + overridenToolName;
		FileBasedConfig config = db.getConfig();
		config.setString(CONFIG_DIFFTOOL_SECTION
				CONFIG_KEY_PATH

		DiffTools manager = new DiffTools(db);
		Map<String
				.getAvailableTools();
		ExternalDiffTool externalDiffTool = availableTools
				.get(overridenToolName);
		String actualDiffToolPath = externalDiffTool.getPath();
		assertEquals(
				"Expected pre-defined external diff tool to have overriden path"
				overridenToolPath
		String expectedDiffToolCommand = overridenToolPath + " "
				+ overridenTool.getParameters();
		String actualDiffToolCommand = externalDiffTool.getCommand();
		assertEquals(
				"Expected pre-defined external diff tool to have overriden command"
				expectedDiffToolCommand
	}

	@Test(expected = ToolException.class)
	public void testUndefinedTool() throws Exception {
		DiffTools manager = new DiffTools(db);

		String toolName = "undefined";
		BooleanTriState prompt = BooleanTriState.UNSET;
		BooleanTriState gui = BooleanTriState.UNSET;
		BooleanTriState trustExitCode = BooleanTriState.UNSET;

		manager.compare(db
				gui
		fail("Expected exception to be thrown due to not defined external diff tool");
	}

	private String getEchoCommand() {
		return "(echo \"$LOCAL\" \"$REMOTE\") > "
				+ commandResult.getAbsolutePath();
	}
