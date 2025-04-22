						if (operation == Operation.BEGIN
								&& result.getSeverity() == IStatus.ERROR) {
							if (!repository.getRepositoryState().equals(
									RepositoryState.SAFE)) {
								Throwable t = result.getException();
								try {
									new RebaseOperation(repository,
											Operation.ABORT).execute(null);
									Activator.showError(t.getMessage(), t);
								} catch (CoreException e1) {
									IStatus mStatus = createMultiStatus(t, e1);
									CoreException mStatusException = new CoreException(
											mStatus);
									Activator.showError(
											mStatusException.getMessage(),
											mStatusException);
								}
							}
						}
