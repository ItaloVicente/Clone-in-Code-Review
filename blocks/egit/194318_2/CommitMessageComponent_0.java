			commentCharBeforeAmending = commentChar;
			autoCommentCharBeforeAmending = autoCommentChar;
			if (config.isAutoCommentChar()) {
				commentChar = config.getCommentChar(
						Utils.normalizeLineEndings(previousCommitMessage));
				autoCommentChar = commentChar;
			} else {
				commentChar = config.getCommentChar();
				autoCommentChar = '\0';
			}
			CleanupMode mode = config.resolve(CleanupMode.DEFAULT, true);
			commitText.setCleanupMode(mode, commentChar);

