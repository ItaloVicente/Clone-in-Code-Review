		for (IJobProgressManagerListener listener : listeners) {
			listener.removeGroup(group);
		}
	}

	/**
	 * Refresh the content providers as a result of an addition of info.
	 *
	 * @param info
	 */
	public void addJobInfo(JobInfo info) {
		GroupInfo group = info.getGroupInfo();
		if (group != null) {
			refreshGroup(group);
		}

		jobs.put(info.getJob(), info);
		for (IJobProgressManagerListener listener : listeners) {
			if (!isCurrentDisplaying(info.getJob(), listener.showsDebug())) {
				listener.addJob(info);
