		try {
			IJobManager jobMan = Job.getJobManager();
			jobMan.join(JobFamilies.GITFLOW_FAMILY, null);
		} catch (OperationCanceledException | InterruptedException e) {
			return error(e.getMessage(), e);
		}
