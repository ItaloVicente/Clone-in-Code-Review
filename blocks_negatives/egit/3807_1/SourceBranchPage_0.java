	private void checkForEmptyRepo() {
		if (isSourceRepoEmpty()) {
			setErrorMessage(null);
			setMessage(UIText.SourceBranchPage_repoEmpty, IMessageProvider.WARNING);
			setPageComplete(true);
		}
	}

