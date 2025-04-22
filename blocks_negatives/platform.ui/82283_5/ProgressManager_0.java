	/**
	 * Return an existing job info for the given Job or <code>null</code> if
	 * there isn't one.
	 *
	 * @param job
	 * @return JobInfo
	 */
	JobInfo internalGetJobInfo(Job job) {
		return jobs.get(job);
	}

