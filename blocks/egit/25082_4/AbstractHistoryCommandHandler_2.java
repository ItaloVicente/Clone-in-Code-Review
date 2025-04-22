	protected ObjectId getSelectedCommitId(ExecutionEvent event)
			throws ExecutionException {
		IStructuredSelection selection = getSelection(event);
		if (selection.size() != 1)
			throw new ExecutionException(
					UIText.AbstractHistoryCommandHandler_ActionRequiresOneSelectedCommitMessage);

		RevCommit commit = (RevCommit) selection.getFirstElement();
		return commit.getId();
	}

	protected RevCommit getSelectedCommit(ExecutionEvent event)
			throws ExecutionException {
		List<RevCommit> commits = getSelectedCommits(event);
		if (commits.size() != 1)
			throw new ExecutionException(
					UIText.AbstractHistoryCommandHandler_ActionRequiresOneSelectedCommitMessage);
		return commits.get(0);
	}

