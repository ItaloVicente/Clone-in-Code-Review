					IWorkspaceRunnable wsr = new IWorkspaceRunnable() {

						public void run(IProgressMonitor actMonitor)
								throws CoreException {

							for (IProject prj : projectsToDelete)
								prj.delete(false, false, actMonitor);
						}
					};

					try {
						ResourcesPlugin.getWorkspace().run(wsr,
								ResourcesPlugin.getWorkspace().getRoot(),
								IWorkspace.AVOID_UPDATE, monitor);
					} catch (CoreException e1) {
						Activator.logError(e1.getMessage(), e1);
					}
