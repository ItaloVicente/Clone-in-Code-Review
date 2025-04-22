		String formatIssue = formatIssuesInCommitMessage(
				commitText.getDocument());
		if (formatIssue != null) {
			return new CommitStatus(formatIssue, IMessageProvider.WARNING);
		}

