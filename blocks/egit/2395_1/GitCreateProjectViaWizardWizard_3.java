			try {
				IWorkspaceRunnable wsr = new IWorkspaceRunnable() {
					public void run(IProgressMonitor actMonitor)
							throws CoreException {
						final IProjectDescription desc = ResourcesPlugin
								.getWorkspace().newProjectDescription(
										projectName[0]);
						if (!defaultLocation[0])
							desc.setLocation(new Path(myGitDir));

						IProject prj = ResourcesPlugin.getWorkspace().getRoot()
								.getProject(desc.getName());
						prj.create(desc, actMonitor);
						prj.open(actMonitor);

						ResourcesPlugin.getWorkspace().getRoot().refreshLocal(
								IResource.DEPTH_ONE, actMonitor);

						File repoDir = myRepository.getDirectory();
						ConnectProviderOperation cpo = new ConnectProviderOperation(
								prj, repoDir);
						cpo.execute(new NullProgressMonitor());
					}
				};
				ResourcesPlugin.getWorkspace().run(wsr, monitor);
