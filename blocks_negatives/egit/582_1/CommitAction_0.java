		Repository[] repos = getRepositoriesFor(getProjectsForSelectedResources());
		Repository repository = null;
		amendAllowed = repos.length == 1;
		for (Repository repo : repos) {
			repository = repo;
			if (!repo.getRepositoryState().canCommit()) {
				MessageDialog.openError(getTargetPart().getSite().getShell(),
					UIText.CommitAction_cannotCommit,
					NLS.bind(UIText.CommitAction_repositoryState, repo.getRepositoryState().getDescription()));
				return;
			}
		}

