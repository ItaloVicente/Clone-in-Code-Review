		if (commentChar == '\0') {
			commentChar = config
					.getCommentChar(Utils.normalizeLineEndings(initialMessage));
		}
		commitDialog.setCleanupMode(mode, commentChar);
		commitDialog.setCommitMessage(initialMessage);
