					if (!RepositoryCache.FileKey.isGitRepository(file)) {
						errorMessage = NLS
								.bind(
										UIText.RepositoriesView_ClipboardContentNoGitRepoMessage,
										content);
						return;
