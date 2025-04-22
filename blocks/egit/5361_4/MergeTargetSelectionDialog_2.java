		String branch = getCurrentBranch();
		if (branch != null)
			return MessageFormat.format(
					UIText.MergeTargetSelectionDialog_TitleMergeWithBranch,
					branch);
		else
			return UIText.MergeTargetSelectionDialog_TitleMerge;
