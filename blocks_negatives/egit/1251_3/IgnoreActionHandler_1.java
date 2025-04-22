
		WorkspaceJob job = new WorkspaceJob(UIText.IgnoreAction_jobName) {
			public IStatus runInWorkspace(IProgressMonitor monitor)
					throws CoreException {
				monitor.beginTask(UIText.IgnoreAction_taskName,
						resources.length);
