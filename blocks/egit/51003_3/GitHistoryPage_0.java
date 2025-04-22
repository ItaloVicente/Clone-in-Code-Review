	private void loadInitialHistory(@NonNull RevWalk walk) {
		job = new GenerateHistoryJob(this, graph.getControl(), walk, resources);
		job.setRule(this);
		job.setLoadHint(INITIAL_ITEM);
		if (trace)
			GitTraceLocation.getTrace().trace(
					GitTraceLocation.HISTORYVIEW.getLocation(),
					"Scheduling initial GenerateHistoryJob"); //$NON-NLS-1$
		schedule(job);
	}

