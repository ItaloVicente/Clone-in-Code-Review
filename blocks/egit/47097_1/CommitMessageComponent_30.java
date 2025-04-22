		String message = commitText.getCommitMessage();
		String formatIssue = formatIssuesInCommitMessage(message);
		if (formatIssue != null) {
			return new CommitStatus(formatIssue, IMessageProvider.WARNING);
		}

