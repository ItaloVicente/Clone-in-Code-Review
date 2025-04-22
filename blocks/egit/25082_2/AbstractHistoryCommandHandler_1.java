	protected IStructuredSelection getSelection(ExecutionEvent event)
			throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelectionChecked(event);
		if (selection instanceof IStructuredSelection)
			return (IStructuredSelection) selection;
		else
			return StructuredSelection.EMPTY;
	}

	protected ObjectId getSelectedCommitId(ExecutionEvent event)
			throws ExecutionException {
		IStructuredSelection selection = getSelection(event);
		if (selection.isEmpty())
			throw new ExecutionException(
					UIText.AbstractHistoryCommandHandler_CouldNotGetSelectedCommitMessage);

		RevCommit commit = (RevCommit) selection.getFirstElement();
		return commit.getId();
	}

	protected RevCommit getSelectedCommit(ExecutionEvent event)
			throws ExecutionException {
		List<RevCommit> commits = getSelectedCommits(event);
		if (commits.size() != 1)
			throw new ExecutionException(
					UIText.AbstractHistoryCommandHandler_CouldNotGetSelectedCommitMessage);
		return commits.get(0);
	}

