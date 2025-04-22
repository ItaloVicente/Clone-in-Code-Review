
	@Test
	public void testOverridePreDefinedToolPath() {
		String newToolPath = "/tmp/path/";

		CommandLineMergeTool[] defaultTools = CommandLineMergeTool.values();
		assertTrue("Expected to find pre-defined external merge tools"
				defaultTools.length > 0);

		CommandLineMergeTool overridenTool = defaultTools[0];
		String overridenToolName = overridenTool.name();
		String overridenToolPath = newToolPath + overridenToolName;
		FileBasedConfig config = db.getConfig();
		config.setString(CONFIG_MERGETOOL_SECTION
				CONFIG_KEY_PATH

		MergeTools manager = new MergeTools(db);
		Map<String
				.getAvailableTools();
		ExternalMergeTool externalMergeTool = availableTools
				.get(overridenToolName);
		String actualMergeToolPath = externalMergeTool.getPath();
		assertEquals(
				"Expected pre-defined external merge tool to have overriden path"
				overridenToolPath
		boolean withBase = true;
		String expectedMergeToolCommand = overridenToolPath + " "
				+ overridenTool.getParameters(withBase);
		String actualMergeToolCommand = externalMergeTool.getCommand();
		assertEquals(
				"Expected pre-defined external merge tool to have overriden command"
				expectedMergeToolCommand
	}

	@Test(expected = ToolException.class)
	public void testUndefinedTool() throws Exception {
		MergeTools manager = new MergeTools(db);

		String toolName = "undefined";
		BooleanTriState prompt = BooleanTriState.UNSET;
		BooleanTriState gui = BooleanTriState.UNSET;

		manager.merge(db
				prompt
		fail("Expected exception to be thrown due to not defined external merge tool");
	}

	private String getEchoCommand() {
		return "(echo \"$LOCAL\" \"$REMOTE\") > "
				+ commandResult.getAbsolutePath();
	}
