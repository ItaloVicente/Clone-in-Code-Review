		if (GitTraceLocation.REFRESH.isActive()) {
			GitTraceLocation.getTrace().trace(
					GitTraceLocation.REFRESH.getLocation(),
							+ toRefresh.size());
		}
		for (Map.Entry<IResource, Boolean> entry : toRefresh.entrySet()) {
			entry.getKey()
					.refreshLocal(entry.getValue().booleanValue()
							? IResource.DEPTH_INFINITE
							: IResource.DEPTH_ONE, null);
		}
		if (GitTraceLocation.REFRESH.isActive()) {
			GitTraceLocation.getTrace().trace(
					GitTraceLocation.REFRESH.getLocation(),
							+ toRefresh.size());
		}
