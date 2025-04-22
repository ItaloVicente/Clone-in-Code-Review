	private void addHeadChangedWarning(String commitMessage,
			char commentStart) {
		String warning = new StringBuilder().append(commentStart).append(' ')
				.append(UIText.StagingView_headCommitChanged).toString();
		if (!commitMessage.startsWith(warning)) {
			String message = warning + Text.DELIMITER + Text.DELIMITER
					+ commitMessage;
