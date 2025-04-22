			new ProgressMonitorDialog(shell).run(true, false,
					new IRunnableWithProgress() {
						@Override
						public void run(IProgressMonitor monitor)
								throws InvocationTargetException,
								InterruptedException {
							deleteBranches(unmergedNodesRef.get(), true, monitor);
						}
					});
		} catch (InvocationTargetException e1) {
			Activator.handleError(
					UIText.RepositoriesView_BranchDeletionFailureMessage, e1
							.getCause(), true);
		} catch (InterruptedException e1) {
