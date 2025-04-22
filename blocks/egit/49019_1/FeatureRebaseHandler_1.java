		IJobManager jobMan = Job.getJobManager();
		try {
			jobMan.join(GITFLOW_FAMILY, null);
		} catch (OperationCanceledException | InterruptedException e) {
			return error(e.getMessage(), e);
		}
