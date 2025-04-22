			try {
				workspace.run(new IWorkspaceRunnable() {
					@Override
					public void run(IProgressMonitor m) throws CoreException {
						SubMonitor subMonitor = SubMonitor.convert(m,
								UIText.Activator_refreshingProjects,
								toRefresh.size());
						for (IProject p : toRefresh) {
							if (subMonitor.isCanceled()) {
								return;
