		taskInfo = null;
	}

	private int compareJobs(JobInfo jobInfo) {

		Job job2 = jobInfo.getJob();

		if (job.isUser()) {
			if (!job2.isUser()) {
