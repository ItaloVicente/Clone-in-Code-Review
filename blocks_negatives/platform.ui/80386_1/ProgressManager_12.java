		return job.getProperty(ProgressManagerUtil.INFRASTRUCTURE_PROPERTY) != null;
	}

	/**
	 * Return the current job infos filtered on debug mode.
	 *
	 * @param debug
	 * @return JobInfo[]
	 */
	public JobInfo[] getJobInfos(boolean debug) {
		return jobs.entrySet().stream().filter(entry -> !isCurrentDisplaying(entry.getKey(), debug))
				.toArray(JobInfo[]::new);
