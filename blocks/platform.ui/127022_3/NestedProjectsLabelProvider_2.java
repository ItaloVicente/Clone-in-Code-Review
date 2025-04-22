	@Override
	protected int getHighestProblemSeverity(IResource resource) {
		int problemSeverity = super.getHighestProblemSeverity(resource);
		if (resource instanceof IContainer && problemSeverity < IMarker.SEVERITY_ERROR) {
			try {
				final CompletableFuture<Map<IContainer, Integer>> severitiesSnapshot = severitiesPerContainer;
				if (severitiesSnapshot != null) {
					Integer severity = severitiesSnapshot.get(50, TimeUnit.MILLISECONDS).get(resource);
					if (severity != null) {
						problemSeverity = Math.max(problemSeverity, severity.intValue());
					}
				}
			} catch (InterruptedException | ExecutionException | TimeoutException e) {
				WorkbenchNavigatorPlugin.log(e.getMessage(),
						new Status(IStatus.ERROR, WorkbenchNavigatorPlugin.PLUGIN_ID, e.getMessage(), e));
			}
		}
		return problemSeverity;
	}

