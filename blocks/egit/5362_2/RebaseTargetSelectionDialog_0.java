		String branch;
		try {
			branch = repo.getBranch();
		} catch (IOException e) {
			branch = null;
		}
		if (branch != null)
			return MessageFormat.format(
					UIText.RebaseTargetSelectionDialog_DialogMessageWithBranch, branch);
		else
			return UIText.RebaseTargetSelectionDialog_DialogMessage;
