			initialMessage = helper.getCommitTemplate();
			commentChar = '\0';
		} else {
			initialMessage = helper.getCommitMessage();
			commentChar = helper.getCommentChar();
		}
		if (config.isAutoCommentChar() || commentChar != '\0') {
			if (commentChar == '\0') {
				commentChar = config.getCommentChar(
						Utils.normalizeLineEndings(initialMessage));
			}
			commitMessageComponent.setAutoCommentChar(commentChar);
