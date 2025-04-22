		if (org.eclipse.egit.ui.Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.COMMIT_DIALOG_CHECK_SECOND_LINE)) {
			String message = commitText.getCommitMessage();
			String formatIssue = formatIssuesInCommitMessage(message);
			if (formatIssue != null) {
				return new CommitStatus(formatIssue, IMessageProvider.WARNING);
			}
