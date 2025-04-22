		if (type == ResetType.HARD) {
			IWorkspaceRunnable action = new IWorkspaceRunnable() {
				public void run(IProgressMonitor monitor) throws CoreException {
					reset(monitor);
				}
			};
			ResourcesPlugin.getWorkspace().run(action, monitor);
		} else {
			reset(monitor);
		}
	}

	private void reset(IProgressMonitor monitor) throws CoreException {
