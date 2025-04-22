	public GitHistoryPage() {
		trace = GitTraceLocation.HISTORYVIEW.isActive();
		if (trace)
			GitTraceLocation.getTrace().traceEntry(
					GitTraceLocation.HISTORYVIEW.getLocation());
	}

