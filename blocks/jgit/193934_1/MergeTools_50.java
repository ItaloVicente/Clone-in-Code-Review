	public Optional<ExecutionResult> merge(FileElement localFile
			FileElement remoteFile
			FileElement baseFile
			BooleanTriState prompt
			PromptContinueHandler promptHandler
			InformNoToolHandler noToolHandler) throws ToolException {

		String toolNameToUse;

		if (toolName == null) {
			throw new ToolException(JGitText.get().diffToolNullError);
		}

		if (toolName.isPresent()) {
			toolNameToUse = toolName.get();
		} else {
			toolNameToUse = getDefaultToolName(gui);

			if (StringUtils.isEmptyOrNull(toolNameToUse)) {
				noToolHandler.inform(new ArrayList<>(predefinedTools.keySet()));
				toolNameToUse = getFirstAvailableTool();
			}
		}

		if (StringUtils.isEmptyOrNull(toolNameToUse)) {
			throw new ToolException(JGitText.get().diffToolNotGivenError);
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
	}

