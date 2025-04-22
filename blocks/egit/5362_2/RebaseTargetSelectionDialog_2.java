		String branch;
		try {
			branch = repo.getBranch();
		} catch (IOException e) {
			branch = null;
		}
		if (branch != null)
			return MessageFormat.format(UIText.RebaseTargetSelectionDialog_RebaseTitleWithBranch, branch);
		else
			return UIText.RebaseTargetSelectionDialog_RebaseTitle;
