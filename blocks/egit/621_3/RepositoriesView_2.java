						final String refName = ref.getLeaf().getName();
						Job job = new Job(NLS.bind(
								UIText.RepositoriesView_CheckingOutMessage,
								refName)) {

							@Override
							protected IStatus run(IProgressMonitor monitor) {

								Repository repo = node.getRepository();

								final BranchOperation op = new BranchOperation(
										repo, refName);
								IWorkspaceRunnable wsr = new IWorkspaceRunnable() {

									public void run(IProgressMonitor myMonitor)
											throws CoreException {
										op.run(myMonitor);
									}
								};

								try {
									ResourcesPlugin.getWorkspace().run(
											wsr,
											ResourcesPlugin.getWorkspace()
													.getRoot(),
											IWorkspace.AVOID_UPDATE, monitor);
									scheduleRefresh();
								} catch (CoreException e1) {
									return e1.getStatus();
