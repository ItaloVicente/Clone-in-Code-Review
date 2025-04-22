	public Optional<ExecutionResult> compare(FileElement localFile
			FileElement remoteFile
			Optional<Boolean> prompt
			Optional<Boolean> trustExitCode
			PromptContinueHandler promptHandler
			InformNoToolHandler noToolHandler) throws ToolException {

		String toolNameToUse;

		if (toolName.isPresent()) {
			toolNameToUse = toolName.get();
		} else {
			toolNameToUse = getDefaultToolName(gui);

			if (toolNameToUse == null || toolNameToUse.isEmpty()) {
				noToolHandler.inform(new ArrayList<>(predefinedTools.keySet()));
				toolNameToUse = getFirstAvailableTool();
			}
		}

		@SuppressWarnings("boxing")
		boolean doPrompt = prompt.orElse(isPrompt());

		if (doPrompt) {
			if (!promptHandler.prompt(toolNameToUse)) {
				return Optional.empty();
			}
		}

		@SuppressWarnings("boxing")
		boolean trust = trustExitCode.orElse(config.isTrustExitCode());

		return Optional.of(
				compare(localFile
	}

