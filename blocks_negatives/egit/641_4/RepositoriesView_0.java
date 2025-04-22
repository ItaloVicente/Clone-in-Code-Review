						final String refName = ref.getLeaf().getName();
						Job job = new Job(NLS.bind(
								UIText.RepositoriesView_CheckingOutMessage,
								refName)) {

							@Override
							protected IStatus run(IProgressMonitor monitor) {

								Repository repo = node.getRepository();

								final BranchOperation op = new BranchOperation(
										repo, refName);
								try {
									op.execute(monitor);
									scheduleRefresh();
								} catch (CoreException e1) {
									return new Status(IStatus.ERROR, Activator
											.getPluginId(), e1.getMessage(), e1);
								}
								return Status.OK_STATUS;
							}
						};

						job.setUser(true);
						job.schedule();
