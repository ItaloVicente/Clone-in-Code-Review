	private void checkoutBranch(final RepositoryTreeNode node,
			final String refName) {
		Job job = new Job(NLS.bind(UIText.RepositoriesView_CheckingOutMessage,
				refName)) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {

				Repository repo = node.getRepository();

				final BranchOperation op = new BranchOperation(repo, refName);
				IWorkspaceRunnable wsr = new IWorkspaceRunnable() {

					public void run(IProgressMonitor myMonitor)
							throws CoreException {
						op.execute(myMonitor);
					}
				};

				try {
					ResourcesPlugin.getWorkspace().run(wsr,
							ResourcesPlugin.getWorkspace().getRoot(),
							IWorkspace.AVOID_UPDATE, monitor);
					scheduleRefresh();
				} catch (CoreException e1) {
					return new Status(IStatus.ERROR, Activator.getPluginId(),
							e1.getMessage(), e1);
				}

				return Status.OK_STATUS;
			}
		};

		job.setUser(true);
		job.schedule();
	}

