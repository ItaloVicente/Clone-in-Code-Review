		cancelRunningSubmoduleJobs();
		super.dispose();
	}

	private void cancelRunningSubmoduleJobs() {
		Job.getJobManager().cancel(JobFamilies.WALK_SUBMODULES);
