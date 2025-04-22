		String commitMsg = commitMessageComponent.getCommitMessage();
		if (commitMsg == null || commitMsg.trim().length() == 0) {
			message = UIText.CommitDialog_Message;
			type = IMessageProvider.INFORMATION;
		} else if (!isCommitWithoutFilesAllowed()) {
			message = UIText.CommitDialog_MessageNoFilesSelected;
			type = IMessageProvider.INFORMATION;
