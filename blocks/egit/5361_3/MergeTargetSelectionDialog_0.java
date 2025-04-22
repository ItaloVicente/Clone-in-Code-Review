		String branch;
		try {
			branch = repo.getBranch();
		} catch (IOException e) {
			branch = null;
		}
		if (branch != null)
			return MessageFormat.format(
					UIText.MergeTargetSelectionDialog_SelectRefWithBranch,
					branch);
		else
			return UIText.MergeTargetSelectionDialog_SelectRef;
