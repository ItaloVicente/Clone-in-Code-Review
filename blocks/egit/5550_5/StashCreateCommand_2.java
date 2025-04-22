		InputDialog commitMessageDialog = new InputDialog(shell,
				UIText.StashCreateCommand_titleEnterCommitMessage,
				UIText.StashCreateCommand_messageEnterCommitMessage,
				null, null);
		if (commitMessageDialog.open() != Window.OK)
			return null;
		String message = commitMessageDialog.getValue();
		if (message.length() == 0)
			message = null;

		final StashCreateOperation op = new StashCreateOperation(repo, message);
