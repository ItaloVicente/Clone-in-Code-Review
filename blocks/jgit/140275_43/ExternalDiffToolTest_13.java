	@Test
	public void testUserDefinedToolWithPrompt() throws Exception {
		String command = getEchoCommand();

		FileBasedConfig config = db.getConfig();
		String customToolName = "customTool";
		config.setString(CONFIG_DIFFTOOL_SECTION
				CONFIG_KEY_CMD

		DiffTools manager = new DiffTools(db);

		PromptHandler promptHandler = PromptHandler.acceptPrompt();
		MissingToolHandler noToolHandler = new MissingToolHandler();

		manager.compare(local
				BooleanTriState.TRUE
				promptHandler

		assertEchoCommandHasCorrectOutput();

		List<String> actualToolPrompts = promptHandler.toolPrompts;
		List<String> expectedToolPrompts = Arrays.asList("customTool");
		assertEquals("Expected a user prompt for custom tool call"
				expectedToolPrompts

		assertEquals("Expected to no informing about missing tools"
				Collections.EMPTY_LIST
