	public void start() {
		String jobName = NLS.bind(
				CoreText.PushConfiguredRemoteAction_PushJobName, Activator
						.getDefault().getRepositoryUtil().getRepositoryName(
								localDb), rc.getName());
		JobUtil.scheduleUserJob(this, jobName, JobFamilies.PUSH, this);
	}
