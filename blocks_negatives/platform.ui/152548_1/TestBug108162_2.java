		workspace.run(new IWorkspaceRunnable() {
			@Override
			public void run(IProgressMonitor monitor) {
				ProgressMonitorDialog dialog = new ProgressMonitorDialog(new Shell());
				try {
					dialog.run(true, false, new LockAcquiringOperation());
					assertTrue("Should not get here", false);
				} catch (InvocationTargetException | InterruptedException | IllegalStateException e) {
				}
