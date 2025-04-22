		final CommitOperation commitOperation;
		try {
			commitOperation= new CommitOperation(
					repo,
					commitDialog.getSelectedFiles(), notIndexed, notTracked,
					commitDialog.getAuthor(), commitDialog.getCommitter(),
					commitDialog.getCommitMessage());
		} catch (CoreException e1) {
			Activator.handleError(UIText.CommitUI_commitFailed, e1, true);
			return;
