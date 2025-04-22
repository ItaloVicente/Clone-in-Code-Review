					String message = NLS
							.bind(
									UIText.RepositoriesView_ExceptionLookingUpRepoMessage,
									repoDir.getPath());
					Activator.handleError(message, e, false);
					repositoryUtil.removeDir(repoDir);
