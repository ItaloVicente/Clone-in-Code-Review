			});
			try {
				IWorkspaceRunnable wsr = new IWorkspaceRunnable() {
					public void run(IProgressMonitor actMonitor)
							throws CoreException {
						final IProjectDescription desc = ResourcesPlugin
								.getWorkspace().newProjectDescription(
										projectName[0]);
						desc.setLocation(new Path(path[0]));

						IProject prj = ResourcesPlugin.getWorkspace().getRoot()
								.getProject(desc.getName());
						prj.create(desc, actMonitor);
						prj.open(actMonitor);
						ConnectProviderOperation cpo = new ConnectProviderOperation(
								prj, repoDir[1]);
						cpo.execute(new NullProgressMonitor());

						ResourcesPlugin.getWorkspace().getRoot().refreshLocal(
								IResource.DEPTH_ONE, actMonitor);
					}
				};
				ResourcesPlugin.getWorkspace().run(wsr, monitor);
			} catch (CoreException e) {
				throw new InvocationTargetException(e);
