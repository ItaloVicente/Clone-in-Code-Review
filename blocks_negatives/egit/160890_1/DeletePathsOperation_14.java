		IWorkspaceRunnable action = new IWorkspaceRunnable() {
			@Override
			public void run(IProgressMonitor actMonitor) throws CoreException {
				deletePaths(actMonitor);
			}
		};
