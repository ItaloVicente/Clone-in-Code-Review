		if (!unmergedBranches.isEmpty()) {
			MessageDialog messageDialog = new UnmergedBranchDialog(shell,
					unmergedBranches);
			if (messageDialog.open() == Window.OK) {
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
												unmergedBranches.size());
										for (Ref node : unmergedBranches) {
											deleteBranch(repository, node, true);
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
					Activator
							.handleError(
									UIText.RepositoriesView_BranchDeletionFailureMessage,
									e1.getCause(), true);
				} catch (InterruptedException e1) {
				}
