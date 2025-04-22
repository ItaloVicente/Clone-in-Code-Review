	private void commit() {
		if (stagedTableViewer.getTable().getItemCount() == 0
				&& !amendPreviousCommitButton.getSelection()) {
			MessageDialog.openError(getSite().getShell(),
					UIText.StagingView_committingNotPossible,
					UIText.StagingView_noStagedFiles);
			return;
		}
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
