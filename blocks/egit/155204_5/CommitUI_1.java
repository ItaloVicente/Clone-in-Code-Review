
		if (commitHelper.shouldUseCommitTemplate()) {
			commitDialog
					.setCommitMessage(commitHelper.getCommitTemplate().get());
		} else {
			commitDialog.setCommitMessage(commitHelper.getCommitMessage());
		}
