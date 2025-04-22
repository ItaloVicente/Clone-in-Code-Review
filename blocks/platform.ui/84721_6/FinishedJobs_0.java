	@Override
	public void removeJob(JobInfo info) {
		if (keep(info)) {
			synchronized (keptjobinfos) {
				removeDuplicates(info);
				add(info);
