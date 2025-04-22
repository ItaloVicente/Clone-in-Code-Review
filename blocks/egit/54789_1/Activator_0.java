			if (toRefresh.isEmpty()) {
				return Status.OK_STATUS;
			}

			try {
				workspace.run(new IWorkspaceRunnable() {
					@Override
					public void run(IProgressMonitor m) throws CoreException {
						m.beginTask(UIText.Activator_refreshingProjects,
								toRefresh.size());
						for (IProject p : toRefresh) {
							if (m.isCanceled()) {
								return;
							}
							ISchedulingRule rule = p.getWorkspace().getRuleFactory().refreshRule(p);
							try {
								getJobManager().beginRule(rule, m);
								if (p.isAccessible()) {
									p.refreshLocal(IResource.DEPTH_INFINITE, new SubProgressMonitor(m, 1));
								}
							} catch (CoreException e) {
								handleError(UIText.Activator_refreshFailed, e, false);
							} finally {
								getJobManager().endRule(rule);
							}
						}
