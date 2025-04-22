			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IWorkspaceRunnable operation = innerMonitor -> {
				SubMonitor innerProgress = SubMonitor.convert(innerMonitor,
						toRefresh.size());
				if (GitTraceLocation.REPOSITORYCHANGESCANNER.isActive()) {
					GitTraceLocation.getTrace()
							.trace(GitTraceLocation.REPOSITORYCHANGESCANNER
									.getLocation(),
									"Refreshing repository " + workTree + ' ' //$NON-NLS-1$
											+ toRefresh.size());
				}
				for (Map.Entry<IResource, Boolean> entry : toRefresh
						.entrySet()) {
					entry.getKey().refreshLocal(entry.getValue().booleanValue()
							? IResource.DEPTH_INFINITE : IResource.DEPTH_ONE,
							innerProgress.newChild(1));
				}
				if (GitTraceLocation.REPOSITORYCHANGESCANNER.isActive()) {
					GitTraceLocation.getTrace()
							.trace(GitTraceLocation.REPOSITORYCHANGESCANNER
									.getLocation(),
									"Refreshed repository " + workTree + ' ' //$NON-NLS-1$
											+ toRefresh.size());
				}
			};
			workspace.run(operation, null, IWorkspace.AVOID_UPDATE,
					progress.newChild(1));
		}
