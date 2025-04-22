
		Repository[] repos = getRepositoriesFor(getProjectsForSelectedResources());
		if (repos.length == 0) {
			return;
		}

		for (Repository repo : repos) {
			if (!repo.getRepositoryState().canCommit()) {
				MessageDialog
						.openError(getTargetPart().getSite().getShell(),
								UIText.CommitAction_cannotCommit, NLS.bind(
										UIText.CommitAction_repositoryState,
										repo.getRepositoryState()
												.getDescription()));
				return;
			}
		}

