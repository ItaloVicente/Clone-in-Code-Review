		addCollector(newCollector, false);
	}

	void addCollector(IProgressUpdateCollector newCollector, boolean includeFinished) {
		collectors.put(newCollector, includeFinished);
		if (includeFinished) {
			FinishedJobs.getInstance().addListener(finishedJobsListener);
		}
