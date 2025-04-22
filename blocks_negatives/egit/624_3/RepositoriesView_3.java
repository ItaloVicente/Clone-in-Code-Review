								IWorkspaceRunnable wsr = new IWorkspaceRunnable() {

									public void run(IProgressMonitor myMonitor)
											throws CoreException {
										op.run(myMonitor);
									}
								};

