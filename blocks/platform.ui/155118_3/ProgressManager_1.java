	public JobInfo removeJob(Job job) {
		synchronized (runnableMonitors) {
			JobInfo info = progressFor(job).getJobInfo();
			managedJobs.remove(job);
			synchronized (pendingUpdatesMutex) {
				rememberListenersForJob(info, pendingJobRemoval);
			}
			runnableMonitors.remove(job);
			uiRefreshThrottler.throttledExec();
			return info;
		}
	}

