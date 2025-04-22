				final IWorkspaceRunnable r = new IWorkspaceRunnable() {
					@Override
					public void run(IProgressMonitor monitor) throws CoreException {
						op.run(monitor);
					}
				};
