	/**
	 * Returns the JobInfo for the job. If it does not exist create it.
	 *
	 * @param job
	 * @return JobInfo
	 */
	JobInfo getJobInfo(Job job) {
		return jobs.computeIfAbsent(job, JobInfo::new);
	}

	/**
	 * Returns an existing job info for the given Job or <code>null</code> if
	 * there isn't one.
	 *
	 * @param job
	 * @return JobInfo
	 */
	JobInfo internalGetJobInfo(Job job) {
		return jobs.get(job);
	}

