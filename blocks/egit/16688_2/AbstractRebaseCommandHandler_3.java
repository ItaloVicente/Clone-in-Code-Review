						if (operation == Operation.BEGIN
								&& result.getSeverity() == IStatus.ERROR) {
							if (!repository.getRepositoryState().equals(
									RepositoryState.SAFE)) {
								RuntimeException e = new RuntimeException(
										result.getException());
								try {
									new RebaseOperation(repository,
											Operation.ABORT).execute(null);
									throw e;
								} catch (CoreException e1) {
									IStatus multiStatus  = createMultiStatus(result.getException(), e1);
									CoreException multiStatusCoreException = new CoreException(
											multiStatus);
									throw new RuntimeException(
											multiStatusCoreException);
								}
							}
						}
