	/**
	 * Returns whether or not this job is an infrastructure job.
	 *
	 * @param job
	 * @return boolean <code>true</code> if it is never displayed.
	 */
	private boolean isInfrastructureJob(Job job) {
		if (Policy.DEBUG_SHOW_ALL_JOBS)
			return false;
		return job.getProperty(ProgressManagerUtil.INFRASTRUCTURE_PROPERTY) != null;
	}

