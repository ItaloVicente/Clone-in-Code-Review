	private boolean isCommitWithoutFilesAllowed() {
		if (stagedViewer.getTree().getItemCount() > 0)
			return true;

		if (amendPreviousCommitAction.isChecked())
			return true;

		return CommitHelper.isCommitWithoutFilesAllowed(currentRepository);
