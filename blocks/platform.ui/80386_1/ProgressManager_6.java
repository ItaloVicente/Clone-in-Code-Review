	private JobInfo removeJobInfo(Job job) {
		JobInfo info = jobs.remove(job);
		if (info != null) {
			runnableMonitors.remove(job);

			if (shouldDisplay(job, showSystemJobs)) {
				display.asyncExec(() -> {
					for (IJobProgressManagerListener listener : listeners) {
						listener.removeJob(info);
					}
				});
