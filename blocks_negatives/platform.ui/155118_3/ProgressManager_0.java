		Job job = info.getJob();
		managedJobs.remove(job);
		synchronized (pendingUpdatesMutex) {
			rememberListenersForJob(info, pendingJobRemoval);
		}
		runnableMonitors.remove(job);
		uiRefreshThrottler.throttledExec();
