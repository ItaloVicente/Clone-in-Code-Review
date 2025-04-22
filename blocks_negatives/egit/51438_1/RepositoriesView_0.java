		if (scheduledJob != null
				&& (scheduledJob.getState() == Job.RUNNING
						|| scheduledJob.getState() == Job.WAITING || scheduledJob
						.getState() == Job.SLEEPING)) {
			if (trace)
				GitTraceLocation.getTrace().trace(
						GitTraceLocation.REPOSITORIESVIEW.getLocation(),
						"Pending refresh job, returning"); //$NON-NLS-1$
