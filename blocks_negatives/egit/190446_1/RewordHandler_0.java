
	private String promptCommitMessage(final Shell shell, Repository repo,
			RevCommit commit) {
		CommitConfig config = repo.getConfig().get(CommitConfig.KEY);
		CleanupMode mode = config.resolve(CleanupMode.DEFAULT, true);
		CommitMessageEditorDialog dialog = new CommitMessageEditorDialog(shell,
				commit.getFullMessage(), mode, '#');
		return dialog.open() == Window.OK ? dialog.getCommitMessage() : null;
	}
