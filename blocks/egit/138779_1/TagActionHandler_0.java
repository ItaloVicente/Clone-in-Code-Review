		IRepositoryCommit commit = getCommit(getSelection(event));
		CreateTagDialog dialog;
		Repository repo;
		if (commit == null) {
			repo = getRepository(true, event);
			if (repo == null) {
				return null;
			}
			if (!repo.getRepositoryState().canCheckout()) {
				MessageDialog.openError(getShell(event),
						UIText.TagAction_cannotCheckout,
						MessageFormat.format(UIText.TagAction_repositoryState,
								repo.getRepositoryState().getDescription()));
				return null;
			}
