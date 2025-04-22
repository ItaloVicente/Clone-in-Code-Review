		cancelRunningSubmoduleJobs();
		super.dispose();
	}

	private void cancelRunningSubmoduleJobs() {
		Job.getJobManager().cancel(this);
