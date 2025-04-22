	/**
	 * Refreshes all the IJobProgressManagerListener as a result of a change in
	 * the whole model.
	 */
	public void refreshAll() {
		pruneStaleJobs();
