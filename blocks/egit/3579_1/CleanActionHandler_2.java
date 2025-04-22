		Set<String> fileList = op.dryRun();

		String currentBranchName;
		try {
			currentBranchName = repo.getBranch();
		} catch (IOException e) {
			Activator
					.handleError(UIText.TagAction_cannotGetBranchName, e, true);
			return null;
		}

		CleanTreeDialog dialog = new CleanTreeDialog(getShell(event),currentBranchName, repo, fileList);

		if (dialog.open() != IDialogConstants.OK_ID)
			return null;

