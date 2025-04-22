
	private void rewordCommitMessage(Shell activeShell,
			final GitFlowRepository gfRepo) throws CoreException {
		Repository repository = gfRepo.getRepository();
		CommitHelper commitHelper = new CommitHelper(repository);
		CommitDialog commitDialog = new CommitDialog(activeShell);
		commitDialog.setAmending(true);
		commitDialog.setFiles(repository, Collections.<String> emptySet(), null);
		commitDialog.setCommitter(commitHelper.getCommitter());

		if (Window.OK == commitDialog.open()) {
			CommitOperation commitOperation = new CommitOperation(repository,
					commitHelper.getAuthor(), commitHelper.getCommitter(),
					commitDialog.getCommitMessage());
			commitOperation.setAmending(true);
			commitOperation.execute(null);
		}
	}
