					RebaseResult result = rebase.getResult();
					if (result.getStatus() == org.eclipse.jgit.api.RebaseResult.Status.STOPPED) {
						abortedDueToConflict.set(true);
						new RebaseOperation(repository, Operation.ABORT)
								.execute(monitor);
					}
