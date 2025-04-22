
	private void rewordCommitMessage(Shell activeShell,
			final GitFlowRepository gfRepo) throws CoreException, IOException {
		Repository repository = gfRepo.getRepository();
		CommitHelper commitHelper = new CommitHelper(repository);

		CommitMessageEditorDialog messageEditorDialog = new CommitMessageEditorDialog(
				activeShell, repository.readSquashCommitMsg(),
				UIText.FeatureFinishHandler_rewordSquashedCommitMessage);

		if (Window.OK == messageEditorDialog.open()) {
			CommitOperation commitOperation = new CommitOperation(repository,
					commitHelper.getAuthor(), commitHelper.getCommitter(),
					messageEditorDialog.getCommitMessage());
			commitOperation.execute(null);
		}

	}
