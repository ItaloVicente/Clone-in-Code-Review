
	protected static class PromptHandler implements PromptContinueHandler {

		private final boolean promptResult;

		final List<String> toolPrompts = new ArrayList<>();

		private PromptHandler(boolean promptResult) {
			this.promptResult = promptResult;
		}

		static PromptHandler acceptPrompt() {
			return new PromptHandler(true);
		}

		static PromptHandler cancelPrompt() {
			return new PromptHandler(false);
		}

		@Override
		public boolean prompt(String toolName) {
			toolPrompts.add(toolName);
			return promptResult;
		}
	}

	protected static class MissingToolHandler implements InformNoToolHandler {

		final List<String> missingTools = new ArrayList<>();

		@Override
		public void inform(List<String> toolNames) {
			missingTools.addAll(toolNames);
		}
	}
