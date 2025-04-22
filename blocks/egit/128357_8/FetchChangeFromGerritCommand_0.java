		if (repository == null) {
			Repository[] repositories = repositoryCache.getAllRepositories();
			if (repositories.length == 0) {
				Shell shell = getShell(event);
				MessageDialog.openInformation(shell,
						UIText.FetchChangeFromGerritCommand_noRepositorySelectedTitle,
						UIText.FetchChangeFromGerritCommand_noRepositorySelectedMessage);
				return null;
			} else {
				File activeFile = getActiveFile();
				if (activeFile != null) {
					repository = repositoryCache.getRepository(activeFile);
				} else {
					repository = repositories[0];
				}
			}
		}
		if (repository == null) {
