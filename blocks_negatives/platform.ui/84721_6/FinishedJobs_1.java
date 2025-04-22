	public FinishedJobs() {
		listener = new IJobProgressManagerListener() {
			@Override
			public void addJob(JobInfo info) {
				removeDuplicates(info);
			}

			@Override
			public void addGroup(GroupInfo info) {
				removeDuplicates(info);
			}
