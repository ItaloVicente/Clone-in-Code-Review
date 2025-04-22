
		if (commitHelper.shouldUseCommitTemplate()) {
			commitDialog
					.setCommitMessage(commitHelper.getCommitTemplate());
		} else {
			commitDialog.setCommitMessage(commitHelper.getCommitMessage());
		}
