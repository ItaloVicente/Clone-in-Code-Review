	public Optional<ExecutionResult> compare(FileElement localFile
			FileElement remoteFile
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

		boolean trust;
		if (trustExitCode != BooleanTriState.UNSET) {
			trust = trustExitCode == BooleanTriState.TRUE;
		} else {
			trust = config.isTrustExitCode();
		}

		ExternalDiffTool tool = getTool(toolNameToUse);
		if (tool == null) {
			throw new ToolException(
		}

		return Optional.of(
				compare(localFile
	}

