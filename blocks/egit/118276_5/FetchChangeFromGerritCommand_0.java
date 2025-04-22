			Repository[] repositories = Activator.getDefault()
					.getRepositoryCache()
					.getAllRepositories();
			if (repositories.length == 0) {
				Shell shell = getShell(event);
				MessageDialog.openInformation(shell,
						UIText.FetchChangeFromGerritCommand_noRepositorySelectedTitle,
						UIText.FetchChangeFromGerritCommand_noRepositorySelectedMessage);
				return null;
			} else {
				repository = repositories[0];
			}
