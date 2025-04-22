
					if (addDir(file))
						scheduleRefresh();
					else
						errorMessage = NLS.bind(
								UIText.RepositoriesView_PasteRepoAlreadyThere,
								content);
				} finally {
					if (clip != null)
						clip.dispose();
					if (errorMessage != null)
						MessageDialog.openWarning(getSite().getShell(),
								UIText.RepositoriesView_PasteFailureTitle,
								errorMessage);
