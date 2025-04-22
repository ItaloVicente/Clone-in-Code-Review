		userDefinedTools = setupUserDefinedTools(predefinedTools);
	}

	public Optional<ExecutionResult> merge(FileElement localFile
			FileElement remoteFile
			FileElement baseFile
			BooleanTriState prompt
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

		boolean doPrompt;
		if (prompt != BooleanTriState.UNSET) {
			doPrompt = prompt == BooleanTriState.TRUE;
		} else {
			doPrompt = isInteractive();
		}

		if (doPrompt) {
			if (!promptHandler.prompt(toolNameToUse)) {
				return Optional.empty();
			}
		}

		ExternalMergeTool tool = getTool(toolNameToUse);
		if (tool == null) {
			throw new ToolException(
		}

		return Optional.of(merge(localFile
				tempDir
