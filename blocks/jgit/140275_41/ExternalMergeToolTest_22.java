
		PromptHandler promptHandler = PromptHandler.acceptPrompt();
		MissingToolHandler noToolHandler = new MissingToolHandler();

		manager.merge(local
				Optional.of(customToolName)
				promptHandler

		assertEchoCommandHasCorrectOutput();

		List<String> actualToolPrompts = promptHandler.toolPrompts;
		List<String> expectedToolPrompts = Arrays.asList("customTool");
		assertEquals("Expected a user prompt for custom tool call"
				expectedToolPrompts

		assertEquals("Expected to no informing about missing tools"
				Collections.EMPTY_LIST
	}

	@Test
	public void testUserDefinedToolWithCancelledPrompt() throws Exception {
		MergeTools manager = new MergeTools(db);

		PromptHandler promptHandler = PromptHandler.cancelPrompt();
		MissingToolHandler noToolHandler = new MissingToolHandler();

		Optional<ExecutionResult> result = manager.merge(local
				base
				promptHandler
		assertFalse("Expected no result if user cancels the operation"
				result.isPresent());
