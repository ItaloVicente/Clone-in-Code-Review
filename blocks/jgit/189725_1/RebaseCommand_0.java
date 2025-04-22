	private String editCommitMessage(String message
			@NonNull CleanupMode mode) {
		String newMessage;
		CommitConfig.CleanupMode cleanup;
		if (interactiveHandler instanceof InteractiveHandler2) {
			InteractiveHandler2.ModifyResult modification = ((InteractiveHandler2) interactiveHandler)
					.editCommitMessage(message
			newMessage = modification.getMessage();
			cleanup = modification.getCleanupMode();
			if (CleanupMode.DEFAULT.equals(cleanup)) {
				cleanup = mode;
			}
		} else {
			newMessage = interactiveHandler.modifyCommitMessage(message);
			cleanup = CommitConfig.CleanupMode.STRIP;
		}
		return CommitConfig.cleanText(newMessage
	}

