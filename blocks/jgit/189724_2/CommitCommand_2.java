			CommitConfig config = null;
			if (CleanupMode.DEFAULT.equals(cleanupMode)) {
				config = repo.getConfig().get(CommitConfig.KEY);
				cleanupMode = config.resolve(cleanupMode
			}
			char comments;
			if (commentChar == null) {
			} else {
				comments = commentChar.charValue();
			}
			message = CommitConfig.cleanText(message

