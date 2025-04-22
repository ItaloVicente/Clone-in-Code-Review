    /**
     * Refresh the supplied JobInfo.
     * @param info
     */
    public void refresh(JobInfo info) {
		currentInfo.refresh(info);
		GroupInfo group = info.getGroupInfo();
		if (group != null) {
			currentInfo.refresh(group);
		}
		throttledUpdate.throttledExec();

    }

