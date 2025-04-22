	public Optional<ExecutionResult> merge(FileElement localFile
			FileElement remoteFile
			FileElement baseFile
			Optional<Boolean> prompt
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

		return Optional.of(
				merge(localFile
						getTool(toolNameToUse)));
	}

