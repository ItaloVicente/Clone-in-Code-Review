		String oldMessage = oldState.getCommitMessage(); // Raw, not cleaned
		CommitConfig config = commitMessageComponent.getRepository().getConfig()
				.get(CommitConfig.KEY);
		char commentChar = oldState.getAutoCommentChar();
		if (config.isAutoCommentChar() || commentChar != '\0') {
			if (commentChar == '\0') {
				commentChar = config
						.getCommentChar(Utils.normalizeLineEndings(oldMessage));
			}
			commitMessageComponent.setAutoCommentChar(commentChar);
		} else {
			commentChar = config.getCommentChar();
			commitMessageComponent.setAutoCommentChar('\0');
		}
		commitMessageComponent.setCommentChar(commentChar);
		CleanupMode mode = config.resolve(CleanupMode.DEFAULT, true);
		commitMessageText.setCleanupMode(mode, commentChar);
