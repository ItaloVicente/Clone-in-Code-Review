			}
		};
		updateJob.setSystem(true);
		updateJob.setPriority(Job.DECORATE);
		updateJob.setProperty(ProgressManagerUtil.INFRASTRUCTURE_PROPERTY, new Object());

	}

	UpdatesInfo getCurrentInfo() {
		return currentInfo;
	}

	public void refresh(JobInfo info) {

		if (isUpdateJob(info.getJob())) {
