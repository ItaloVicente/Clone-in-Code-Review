	private boolean isCommitWithoutFilesAllowed() {
		if (stagedTableViewer.getTable().getItemCount() > 0)
			return true;

		if (amendPreviousCommitAction.isChecked())
			return true;

		return CommitHelper.isCommitWithoutFilesAllowed(currentRepository);
	}

