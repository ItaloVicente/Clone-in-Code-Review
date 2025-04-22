		String branch = getCurrentBranch();
		if (branch != null)
			return MessageFormat.format(
					UIText.RebaseTargetSelectionDialog_DialogTitleWithBranch,
					branch);
		else
			return UIText.RebaseTargetSelectionDialog_DialogTitle;
