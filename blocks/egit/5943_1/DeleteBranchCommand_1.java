	private List<RefNode> deleteBranches(final List<RefNode> nodes,
			final boolean forceDeletionOfUnmergedBranches,
			IProgressMonitor progressMonitor) throws InvocationTargetException {
		final List<RefNode> unmergedNodes = new ArrayList<RefNode>();
		try {
			ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {

				public void run(IProgressMonitor monitor) throws CoreException {
					monitor.beginTask(UIText.DeleteBranchCommand_DeletingBranchesProgress, nodes.size());
					for (RefNode refNode : nodes) {
						int result = deleteBranch(refNode, refNode
								.getObject(), forceDeletionOfUnmergedBranches);
						if (result == DeleteBranchOperation.REJECTED_CURRENT) {
							throw new CoreException(
									Activator
									.createErrorStatus(
											UIText.DeleteBranchCommand_CannotDeleteCheckedOutBranch,
											null));
						} else if (result == DeleteBranchOperation.REJECTED_UNMERGED) {
							unmergedNodes.add(refNode);
						} else
							monitor.worked(1);
					}
				}
			}, progressMonitor);

		} catch (CoreException ex) {
			throw new InvocationTargetException(ex);
		} finally {
			progressMonitor.done();
		}
		return unmergedNodes;
	}

