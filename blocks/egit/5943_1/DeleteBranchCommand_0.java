			new ProgressMonitorDialog(shell).run(true, false,
					new IRunnableWithProgress() {
						public void run(IProgressMonitor monitor)
								throws InvocationTargetException,
								InterruptedException {
							List<RefNode> unmergedNodes = deleteBranches(nodes, false, monitor);
							unmergedNodesRef.set(unmergedNodes);
						}
					});
		} catch (InvocationTargetException e1) {
			Activator.handleError(
					UIText.RepositoriesView_BranchDeletionFailureMessage, e1
							.getCause(), true);
		} catch (InterruptedException e1) {
		}
		if (unmergedNodesRef.get().isEmpty())
			return null;
		MessageDialog messageDialog = new UnmergedBranchDialog<RefNode>(
					shell, unmergedNodesRef.get());
		if (messageDialog.open() != Window.OK)
			return null;
		try {
			new ProgressMonitorDialog(shell).run(true, false,
