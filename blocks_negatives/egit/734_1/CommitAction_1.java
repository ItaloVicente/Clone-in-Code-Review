		String commitMessage = commitDialog.getCommitMessage();
		amending = commitDialog.isAmending();
		try {
			performCommit(commitDialog, commitMessage);
		} catch (TeamException e) {
			handle(e, UIText.CommitAction_errorDuringCommit,
					UIText.CommitAction_errorOnCommit);
