
			if (importProjects)
				try {
					final Repository clonedRepo = repository;
					ResourcesPlugin.getWorkspace().run(
							new IWorkspaceRunnable() {
								public void run(IProgressMonitor importMonitor)
										throws CoreException {
									ProjectUtil.createProjects(clonedRepo,
											monitor);
								}
							}, monitor);
				} catch (OperationCanceledException e) {
					throw new InterruptedException();
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				}
				synchronized (this) {
