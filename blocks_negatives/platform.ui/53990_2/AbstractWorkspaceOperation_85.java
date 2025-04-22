			getWorkspace().run(new IWorkspaceRunnable() {
				@Override
				public void run(IProgressMonitor monitor) throws CoreException {
					doExecute(monitor, uiInfo);
				}
			}, getRedoSchedulingRule(), IWorkspace.AVOID_UPDATE, monitor);
