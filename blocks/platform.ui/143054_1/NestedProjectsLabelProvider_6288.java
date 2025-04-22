	protected int getHighestProblemSeverity(IResource resource) {
		int problemSeverity = super.getHighestProblemSeverity(resource);
		if (resource instanceof IContainer && problemSeverity < IMarker.SEVERITY_ERROR) {
			try {
				final CompletableFuture<NestedProjectsProblemsModel> problemsModelSnapshot = refreshModelJob;
				if (problemsModelSnapshot != null) {
					problemSeverity = Math.max(problemSeverity,
							problemsModelSnapshot.get(50, TimeUnit.MILLISECONDS)
									.getMaxSeverityIncludingNestedProjects(resource));
				}
			} catch (InterruptedException | ExecutionException e) {
				WorkbenchNavigatorPlugin.log(e.getMessage(),
						new Status(IStatus.ERROR, WorkbenchNavigatorPlugin.PLUGIN_ID, e.getMessage(), e));
			} catch (TimeoutException e) {
			} catch (RuntimeException e) {
				WorkbenchNavigatorPlugin.log(e.getMessage(),
						new Status(IStatus.ERROR, WorkbenchNavigatorPlugin.PLUGIN_ID, e.getMessage(), e));
			}
		}
		return problemSeverity;
