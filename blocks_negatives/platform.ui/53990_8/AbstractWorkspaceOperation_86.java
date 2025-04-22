			getWorkspace().run(new IWorkspaceRunnable() {
				@Override
				public void run(IProgressMonitor monitor) throws CoreException {
					doUndo(monitor, uiInfo);
				}
			}, getUndoSchedulingRule(), IWorkspace.AVOID_UPDATE, monitor);
