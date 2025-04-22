		IWorkspaceRunnable action = new IWorkspaceRunnable() {
			@Override
			public void run(IProgressMonitor actMonitor) throws CoreException {
				deletePaths(actMonitor);
			}
		};
		ResourcesPlugin.getWorkspace().run(action, getSchedulingRule(),
				IWorkspace.AVOID_UPDATE, m);
