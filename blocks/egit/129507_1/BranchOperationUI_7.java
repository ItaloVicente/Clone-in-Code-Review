	private void showRepositoryInInvalidStateForCheckout(Repository repo) {
		String repoName = Activator.getDefault().getRepositoryUtil()
				.getRepositoryName(repo);
		String description = repo.getRepositoryState().getDescription();
		String message = NLS.bind(UIText.BranchAction_repositoryState, repoName,
				description);

		MessageDialog.openError(getShell(), UIText.BranchAction_cannotCheckout,
				message);
	}

