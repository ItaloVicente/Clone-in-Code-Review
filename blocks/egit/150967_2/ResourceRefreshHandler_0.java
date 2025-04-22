		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRunnable operation = innerMonitor -> {
			SubMonitor innerProgress = SubMonitor.convert(innerMonitor,
					toRefresh.size());
			if (GitTraceLocation.REFRESH.isActive()) {
				GitTraceLocation.getTrace().trace(
						GitTraceLocation.REFRESH.getLocation(),
						"Refreshing repository " + workTree + ' ' //$NON-NLS-1$
								+ toRefresh.size());
			}
			for (Map.Entry<IResource, Boolean> entry : toRefresh.entrySet()) {
				if (innerProgress.isCanceled()) {
					break;
				}
				entry.getKey().refreshLocal(
						entry.getValue().booleanValue()
								? IResource.DEPTH_INFINITE
								: IResource.DEPTH_ONE,
						innerProgress.newChild(1));
			}
			if (GitTraceLocation.REFRESH.isActive()) {
				GitTraceLocation.getTrace().trace(
						GitTraceLocation.REFRESH.getLocation(),
						"Refreshed repository " + workTree + ' ' //$NON-NLS-1$
								+ toRefresh.size());
			}
		};
		workspace.run(operation, null, IWorkspace.AVOID_UPDATE,
				progress.newChild(1));
