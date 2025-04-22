						if (operation == Operation.BEGIN
								&& result.getSeverity() == IStatus.ERROR) {
							if (!repository.getRepositoryState().equals(
									RepositoryState.SAFE)) {
								try {
									new RebaseOperation(repository,
											Operation.ABORT).execute(null);
								} catch (CoreException e) {
									Activator
											.error(UIText.AbstractRebaseCommandHandler_CleanUpFailed,
													e);
								}
							}
						}
