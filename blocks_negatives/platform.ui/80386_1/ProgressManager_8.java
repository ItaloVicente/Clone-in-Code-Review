	JobInfo getJobInfo(Job job) {
		JobInfo info = internalGetJobInfo(job);
		if (info == null) {
			info = new JobInfo(job);
			jobs.put(job, info);
		}
		return info;
	}

	/**
	 * Return an existing job info for the given Job or <code>null</code> if
	 * there isn't one.
	 *
	 * @param job
	 * @return JobInfo
	 */
	JobInfo internalGetJobInfo(Job job) {
		return jobs.get(job);
