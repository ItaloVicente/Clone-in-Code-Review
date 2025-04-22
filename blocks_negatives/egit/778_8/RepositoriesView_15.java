					if (!RepositoryCache.FileKey.isGitRepository(file, FS.DETECTED)) {
						errorMessage = NLS
								.bind(
										UIText.RepositoriesView_ClipboardContentNoGitRepoMessage,
										content);
						return;
