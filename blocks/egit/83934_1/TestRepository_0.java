		ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
			@Override
			public void run(IProgressMonitor monitor) throws CoreException {
				disconnect.execute(null);
			}
		}, project, IWorkspace.AVOID_UPDATE, null);
		TestUtils.waitForJobs(5000, null);
