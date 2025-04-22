		CommitConfig config = repo.getConfig().get(CommitConfig.KEY);
		CleanupMode mode = config.resolve(CleanupMode.DEFAULT, true);
		CommitMessageEditorDialog dialog = new CommitMessageEditorDialog(shell,
				repo, commit.getFullMessage(), mode, '#');
		if (dialog.open() != Window.OK) {
			return null;
		}
		String newMessage = dialog.getCommitMessage();
