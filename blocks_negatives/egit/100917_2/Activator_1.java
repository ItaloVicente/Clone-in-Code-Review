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
							}
							ISchedulingRule rule = p.getWorkspace().getRuleFactory().refreshRule(p);
							try {
								getJobManager().beginRule(rule, subMonitor);
								if (p.isAccessible()) {
									p.refreshLocal(IResource.DEPTH_INFINITE,
											subMonitor.newChild(1));
								}
							} catch (CoreException e) {
								handleError(UIText.Activator_refreshFailed, e, false);
							} finally {
								getJobManager().endRule(rule);
							}
						}
