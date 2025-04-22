	@Test
	public void testDefaultToolExecutionWithPrompt() throws Exception {
		FileBasedConfig config = db.getConfig();
		String subsection = null;
		config.setString("merge"

		String command = getEchoCommand();

		config.setString("mergetool"

		MergeTools manager = new MergeTools(db);

		PromptHandler promptHandler = PromptHandler.acceptPrompt();
		MissingToolHandler noToolHandler = new MissingToolHandler();

		manager.merge(local
				BooleanTriState.TRUE

		assertEchoCommandHasCorrectOutput();
	}

	@Test
	public void testNoDefaultToolName() {
		MergeTools manager = new MergeTools(db);
		boolean gui = false;
		String defaultToolName = manager.getDefaultToolName(gui);
		assertNull("Expected no default tool when none is configured"
				defaultToolName);

		gui = true;
		defaultToolName = manager.getDefaultToolName(gui);
		assertNull("Expected no default tool when none is configured"
				defaultToolName);
	}

	@Test(expected = ToolException.class)
	public void testNullTool() throws Exception {
		MergeTools manager = new MergeTools(db);

		PromptHandler promptHandler = null;
		MissingToolHandler noToolHandler = null;

		Optional<String> tool = null;

		manager.merge(local
				BooleanTriState.TRUE
	}

	@Test(expected = ToolException.class)
	public void testNullToolWithPrompt() throws Exception {
		MergeTools manager = new MergeTools(db);

		PromptHandler promptHandler = PromptHandler.cancelPrompt();
		MissingToolHandler noToolHandler = new MissingToolHandler();

		Optional<String> tool = null;

		manager.merge(local
				BooleanTriState.TRUE
	}

