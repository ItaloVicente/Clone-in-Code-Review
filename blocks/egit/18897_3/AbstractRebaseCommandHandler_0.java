							if (rebase.getResult().getStatus() == Status.UNCOMMITTED_CHANGES) {
								handleUncommittedChanges(rebase, repository,
										rebase.getResult()
												.getUncommittedChanges());
							} else {
								RebaseResultDialog.show(rebase.getResult(),
										repository);
								if (operation == Operation.ABORT) {
									setAmending(false, false);
