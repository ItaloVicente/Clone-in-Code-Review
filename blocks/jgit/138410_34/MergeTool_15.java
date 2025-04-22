		return !showPrompt || hasUserAccepted(
				CLIText.get().mergeToolContinueUnresolvedPaths);
	}

	private boolean isMergeSuccessful(boolean showPrompt) throws IOException {
		return !showPrompt
				|| hasUserAccepted(CLIText.get().mergeToolWasMergeSuccessfull);
	}

	private boolean isLaunch(String toolNamePrompt) throws IOException {
