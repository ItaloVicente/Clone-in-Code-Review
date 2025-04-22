				}
			});
			IWorkspaceRunnable wsr = new IWorkspaceRunnable() {
				public void run(IProgressMonitor actMonitor)
						throws CoreException {
					IProject[] currentProjects = ResourcesPlugin.getWorkspace()
							.getRoot().getProjects();
					for (IProject current : currentProjects) {
						if (!previousProjects.contains(current)) {
							ConnectProviderOperation cpo = new ConnectProviderOperation(
									current, repoDir[0]);
							cpo.execute(actMonitor);
						}
