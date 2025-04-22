			CommitConfig config = null;
			if (CleanupMode.DEFAULT.equals(cleanupMode)) {
				config = repo.getConfig().get(CommitConfig.KEY);
				cleanupMode = config.resolve(cleanupMode
			}
			char comments = (char) 0;
			if (CleanupMode.STRIP.equals(cleanupMode)
					|| CleanupMode.SCISSORS.equals(cleanupMode)) {
				if (commentChar == null) {
					if (config == null) {
						config = repo.getConfig().get(CommitConfig.KEY);
					}
					if (config.isAutoCommentChar()) {
						comments = (char) 0;
						cleanupMode = CleanupMode.WHITESPACE;
					} else {
						comments = config.getCommentChar();
					}
				} else {
					comments = commentChar.charValue();
				}
			}
			message = CommitConfig.cleanText(message

