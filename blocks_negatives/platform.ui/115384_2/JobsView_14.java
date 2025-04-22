			ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
				@Override
				public void run(IProgressMonitor monitor) throws CoreException {
					doRun(duration, monitor);
				}
			}, monitor);
