	@Override
	protected int getHighestProblemSeverity(IResource resource) {
		int problemSeverity = super.getHighestProblemSeverity(resource);
		if (resource instanceof IContainer && problemSeverity < IMarker.SEVERITY_ERROR) {
			try {
				Integer severity = severitiesPerContainer.get(50, TimeUnit.MILLISECONDS).get(resource);
				if (severity != null) {
					problemSeverity = severity.intValue();
				}
			} catch (InterruptedException | ExecutionException | TimeoutException e) {
				WorkbenchNavigatorPlugin.log(e.getMessage(),
						new Status(IStatus.ERROR, WorkbenchNavigatorPlugin.PLUGIN_ID, e.getMessage(), e));
			}
		}
		return problemSeverity;
	}

