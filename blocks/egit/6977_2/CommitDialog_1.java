	private boolean isCommitWithoutFilesAllowed() {
		if (filesViewer.getCheckedElements().length > 0)
			return true;

		if (amendingItem.getSelection())
			return true;

		return CommitHelper.isCommitWithoutFilesAllowed(repository);
	}

