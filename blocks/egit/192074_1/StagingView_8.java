	private void setCleanup(Repository repo) {
		if (repo != null) {
			CommitConfig config = repo.getConfig().get(CommitConfig.KEY);
			CleanupMode mode = config.resolve(CleanupMode.DEFAULT, true);
			char commentChar;
			if (config.isAutoCommentChar()) {
				commentChar = commitMessageComponent.getAutoCommentChar();
				if (commentChar == '\0') {
					String currentMessage = Utils
							.normalizeLineEndings(commitMessageText.getText());
					commentChar = config.getCommentChar(currentMessage);
					commitMessageComponent.setCommentChar(commentChar);
					commitMessageComponent.setAutoCommentChar(commentChar);
				}
			} else {
				commentChar = config.getCommentChar();
				commitMessageComponent.setCommentChar(commentChar);
				commitMessageComponent.setAutoCommentChar('\0');
			}
			commitMessageText.setCleanupMode(mode, commentChar);
		} else {
			commitMessageText.setCleanupMode(CleanupMode.STRIP, '#');
		}
		commitMessageText.invalidatePresentation();
	}

