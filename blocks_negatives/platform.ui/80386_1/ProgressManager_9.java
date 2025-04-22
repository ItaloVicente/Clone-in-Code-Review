	public void removeJobInfo(JobInfo info) {
		Job job = info.getJob();
		jobs.remove(job);
		runnableMonitors.remove(job);

		for (IJobProgressManagerListener listener : listeners) {
			if (!isCurrentDisplaying(info.getJob(), listener.showsDebug())) {
				listener.removeJob(info);
