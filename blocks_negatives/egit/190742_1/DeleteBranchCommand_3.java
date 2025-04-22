		final List<RepositoryTreeNode> nodes = getSelectedNodes(event);
		final Map<Ref, Repository> refs = getRefsToDelete(nodes);
		final AtomicReference<Map<Ref, Repository>> unmergedNodesRef = new AtomicReference<>(
				Collections.emptyMap());
		final Shell shell = getShell(event);

		try {
			new ProgressMonitorDialog(shell).run(true, false,
					new IRunnableWithProgress() {
						@Override
						public void run(IProgressMonitor monitor)
								throws InvocationTargetException,
								InterruptedException {
							Map<Ref, Repository> unmergedNodes = deleteBranches(
									refs, false, monitor);
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
		MessageDialog messageDialog = new UnmergedBranchDialog(
					shell, new ArrayList<>(unmergedNodesRef.get().keySet()));
		if (messageDialog.open() != Window.OK)
			return null;
