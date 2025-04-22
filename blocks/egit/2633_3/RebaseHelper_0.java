					if (!repository.getRepositoryState().equals(
							RepositoryState.SAFE)) {
						try {
							new RebaseOperation(repository, Operation.ABORT)
									.execute(monitor);
						} catch (CoreException e1) {
							return createMultiStatus(e, e1);
						}
