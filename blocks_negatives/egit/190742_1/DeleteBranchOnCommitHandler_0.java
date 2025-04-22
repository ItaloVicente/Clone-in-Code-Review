
		try {
			new ProgressMonitorDialog(shell).run(true, false,
					new IRunnableWithProgress() {
						@Override
						public void run(final IProgressMonitor monitor)
								throws InvocationTargetException,
								InterruptedException {
							try {
								monitor.beginTask(
										UIText.DeleteBranchCommand_DeletingBranchesProgress,
										branchesToDelete.size());
								for (Ref refNode : branchesToDelete) {
									int result = deleteBranch(repository,
											refNode, false);
									if (result == DeleteBranchOperation.REJECTED_CURRENT) {
										throw new CoreException(
												Activator
														.createErrorStatus(
																UIText.DeleteBranchCommand_CannotDeleteCheckedOutBranch,
																null));
									} else if (result == DeleteBranchOperation.REJECTED_UNMERGED) {
										unmergedBranches.add(refNode);
									} else
										monitor.worked(1);
								}
							} catch (CoreException ex) {
								throw new InvocationTargetException(ex);
							} finally {
								monitor.done();
							}
						}
					});
		} catch (InvocationTargetException e1) {
			Activator.handleError(
					UIText.RepositoriesView_BranchDeletionFailureMessage,
					e1.getCause(), true);
		} catch (InterruptedException e1) {
