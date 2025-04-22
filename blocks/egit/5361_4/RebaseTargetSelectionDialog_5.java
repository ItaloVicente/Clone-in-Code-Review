		String branch = getCurrentBranch();
		if (branch != null)
			return MessageFormat.format(
					UIText.RebaseTargetSelectionDialog_RebaseTitleWithBranch,
					branch);
		else
			return UIText.RebaseTargetSelectionDialog_RebaseTitle;
