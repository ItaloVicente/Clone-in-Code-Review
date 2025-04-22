		String branch = getCurrentBranch();
		if (branch != null)
			return MessageFormat.format(
					UIText.RebaseTargetSelectionDialog_DialogMessageWithBranch,
					branch);
		else
			return UIText.RebaseTargetSelectionDialog_DialogMessage;
