	public void loadItem(int item) {
		if (job == null || job.loadMoreItemsThreshold() < item)
			loadHistory(item, null);
	}

	public void loadCommit(RevCommit c) {
		if (job == null)
			return;
		job.setLoadHint(c);
		if (trace)
			GitTraceLocation.getTrace().trace(
					GitTraceLocation.HISTORYVIEW.getLocation(),
					"Scheduling GenerateHistoryJob"); //$NON-NLS-1$
		schedule(job);
	}

	private void loadHistory(final int itemToLoad, RevWalk walk) {
		if (itemToLoad == INITIAL_ITEM) {
			job = new GenerateHistoryJob(this, graph.getControl(), walk);
			job.setRule(this);
		}
		job.setLoadHint(itemToLoad);
