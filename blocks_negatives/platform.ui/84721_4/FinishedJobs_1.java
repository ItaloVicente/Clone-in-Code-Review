	public FinishedJobs() {
		listener = new IJobProgressManagerListener() {
			@Override
			public void addJob(JobInfo info) {
				checkForDuplicates(info);
			}

			@Override
			public void addGroup(GroupInfo info) {
				checkForDuplicates(info);
			}
