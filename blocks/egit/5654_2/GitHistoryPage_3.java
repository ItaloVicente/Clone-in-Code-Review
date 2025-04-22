	public void loadItem(int item) {
		if (job == null || job.loadMoreItemsThreshold() < item)
			loadHistory(item);
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

	private void loadHistory(final int itemToLoad) {
		if (itemToLoad == INITIAL_ITEM) {
			final SWTCommitList list = new SWTCommitList(graph.getControl());
			list.source(currentWalk);
			job = new GenerateHistoryJob(this, list);
			job.setRule(this);
		}
		job.setLoadHint(itemToLoad);
