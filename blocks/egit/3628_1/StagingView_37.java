	private void commit() {
		if (stagedTableViewer.getTable().getItemCount() == 0
				&& !amendPreviousCommitAction.isChecked()) {
			MessageDialog.openError(getSite().getShell(),
					UIText.StagingView_committingNotPossible,
					UIText.StagingView_noStagedFiles);
			return;
		}
		if (!commitMessageComponent.checkCommitInfo())
			return;
		final Repository repository = currentRepository;
		CommitOperation commitOperation = null;
