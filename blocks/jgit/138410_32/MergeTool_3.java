		return yes;
	}

	private boolean isContinueUnresolvedPaths() throws IOException {
		return hasUserAccepted(CLIText.get().mergeToolContinueUnresolvedPaths);
	}

	private boolean isMergeSuccessful() throws IOException {
		return hasUserAccepted(CLIText.get().mergeToolWasMergeSuccessfull);
