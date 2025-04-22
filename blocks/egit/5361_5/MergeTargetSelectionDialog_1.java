		String branch = getCurrentBranch();
		if (branch != null)
			return MessageFormat.format(
					UIText.MergeTargetSelectionDialog_SelectRefWithBranch,
					branch);
		else
			return UIText.MergeTargetSelectionDialog_SelectRef;
