
		PromptHandler promptHandler = PromptHandler.cancelPrompt();
		MissingToolHandler noToolHandler = new MissingToolHandler();

		Optional<ExecutionResult> result = manager.compare(local
				Optional.empty()
				BooleanTriState.TRUE
		assertFalse("Expected no result if user cancels the operation"
				result.isPresent());
