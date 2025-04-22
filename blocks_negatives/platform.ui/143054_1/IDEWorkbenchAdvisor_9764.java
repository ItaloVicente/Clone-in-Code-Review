			IRunnableWithProgress runnable = new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor) {
					try {
						if (applyPolicy)
							monitor = new CancelableProgressMonitorWrapper(
									monitor, p);

						status.merge(((Workspace) ResourcesPlugin
								.getWorkspace()).save(true, true, monitor));
					} catch (CoreException e) {
						status.merge(e.getStatus());
					}
