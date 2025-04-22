			protected IStatus run(IProgressMonitor monitor) {
				try {
					reloadJobIsInitializing = true;
					waitForWorkspaceLock(monitor);
				} finally {
					reloadJobIsInitializing = false;
				}
