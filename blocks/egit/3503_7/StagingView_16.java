	private static String getRepositoryName(Repository repository) {
		String repoName = Activator.getDefault().getRepositoryUtil()
				.getRepositoryName(repository);
		RepositoryState state = repository.getRepositoryState();
		if (state != RepositoryState.SAFE)
			return repoName + '|' + state.getDescription();
		else
			return repoName;
	}

	private void commit() {
		if (!commitMessageComponent.checkCommitInfo())
			return;
		CommitOperation commitOperation = null;
		try {
			commitOperation = new CommitOperation(currentRepository,
					commitMessageComponent.getAuthor(),
					commitMessageComponent.getCommitter(),
					commitMessageComponent.getCommitMessage());
		} catch (CoreException e) {
			Activator.handleError(UIText.StagingView_commitFailed, e, true);
			return;
		}
		if (amendPreviousCommitButton.getSelection())
			commitOperation.setAmending(true);
		commitOperation.setComputeChangeId(addChangeIdButton.getSelection());
		CommitUI.performCommit(currentRepository, commitOperation);
		clearCommitMessageToggles();
		commitMessageText.setText(EMPTY_STRING);
