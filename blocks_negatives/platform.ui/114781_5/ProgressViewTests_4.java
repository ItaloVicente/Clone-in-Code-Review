	protected boolean checkJob(Job job, boolean found, JobInfo jobInfo) {
		if(job.equals(jobInfo.getJob())) {
			if(found) {
				fail("The job is listed twice");
			} else {
				found = true;
			}
		}
		return found;
