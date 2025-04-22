	public ISchedulingRule getSchedulingRule() {
		return null;
	}

	public void start() {
		String jobName = NLS.bind(
				UIText.FetchConfiguredRemoteAction_FetchJobName, repository
						.getDirectory().getParentFile().getName(), remote
						.getName());
		JobUtil.scheduleUserJob(this, jobName, JobFamilies.FETCH, this);
	}
