					try {
						ResourcesPlugin.getWorkspace().run(wsr,
								ResourcesPlugin.getWorkspace().getRoot(),
								IWorkspace.AVOID_UPDATE,
								new NullProgressMonitor());
						scheduleRefresh();
					} catch (CoreException e1) {
						MessageDialog.openError(getSite().getShell(),
								UIText.RepositoriesView_Error_WindowTitle, e1
										.getMessage());
					}
