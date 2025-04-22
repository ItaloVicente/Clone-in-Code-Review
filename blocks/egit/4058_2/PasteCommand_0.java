				file = new File(file, Constants.DOT_GIT_EXT);
				if (!RepositoryCache.FileKey.isGitRepository(file, FS.DETECTED)) {
					errorMessage = NLS
							.bind(UIText.RepositoriesView_ClipboardContentNoGitRepoMessage,
									content);
					return null;
				}
