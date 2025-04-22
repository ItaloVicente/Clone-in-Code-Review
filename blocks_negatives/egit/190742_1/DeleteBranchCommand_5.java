
	private Map<Ref, Repository> deleteBranches(final Map<Ref, Repository> refs,
			final boolean forceDeletionOfUnmergedBranches,
			IProgressMonitor progressMonitor) throws InvocationTargetException {
		final Map<Ref, Repository> unmergedNodes = new LinkedHashMap<>();
		try {
			ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {

				@Override
				public void run(IProgressMonitor monitor) throws CoreException {
					monitor.beginTask(
							UIText.DeleteBranchCommand_DeletingBranchesProgress,
							refs.size());
					for (Entry<Ref, Repository> entry : refs.entrySet()) {
						Repository repository = entry.getValue();
						Ref ref = entry.getKey();
						int result = deleteBranch(repository, ref,
								forceDeletionOfUnmergedBranches);
						if (result == DeleteBranchOperation.REJECTED_CURRENT) {
							throw new CoreException(
									Activator
									.createErrorStatus(
											UIText.DeleteBranchCommand_CannotDeleteCheckedOutBranch,
											null));
						} else if (result == DeleteBranchOperation.REJECTED_UNMERGED) {
							unmergedNodes.put(ref, repository);
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

	private int deleteBranch(final Repository repo, final Ref ref,
			boolean force) throws CoreException {
		DeleteBranchOperation dbop = new DeleteBranchOperation(repo, ref,
				force);
		dbop.execute(null);
		return dbop.getStatus();
	}

