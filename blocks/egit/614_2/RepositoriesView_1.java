	private void scheduleRefresh(long delay) {
		boolean trace = GitTraceLocation.REPOSITORIESVIEW.isActive();
		if (trace)
			GitTraceLocation.getTrace().trace(
					GitTraceLocation.REPOSITORIESVIEW.getLocation(),
					"Entering scheduleRefresh()"); //$NON-NLS-1$

		if (scheduledJob != null
				&& (scheduledJob.getState() == Job.RUNNING
						|| scheduledJob.getState() == Job.WAITING || scheduledJob
						.getState() == Job.SLEEPING)) {
			if (trace)
				GitTraceLocation.getTrace().trace(
						GitTraceLocation.REPOSITORIESVIEW.getLocation(),
						"Pending refresh job, returning"); //$NON-NLS-1$
