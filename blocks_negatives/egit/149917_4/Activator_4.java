		if (GitTraceLocation.REPOSITORYCHANGESCANNER.isActive()) {
			GitTraceLocation.getTrace().trace(
					GitTraceLocation.REPOSITORYCHANGESCANNER.getLocation(),
		}
		refreshJob.cancel();

