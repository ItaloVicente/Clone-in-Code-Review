	public JobInfo removeJob(Job job) {
		synchronized (runnableMonitors) {
			JobInfo info = progressFor(job).getJobInfo();
			managedJobs.remove(job);
			synchronized (pendingUpdatesMutex) {
				Predicate<IJobProgressManagerListener> predicate = listener -> !isNeverDisplaying(info.getJob(), listener.showsDebug());
				rememberListenersForJob(info, pendingJobRemoval, predicate);
			}
			runnableMonitors.remove(job);
			uiRefreshThrottler.throttledExec();
			return info;
		}
	}

